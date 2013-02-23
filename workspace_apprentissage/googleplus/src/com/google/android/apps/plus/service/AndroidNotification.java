package com.google.android.apps.plus.service;

import android.app.Notification;
import android.app.Notification.BigPictureStyle;
import android.app.Notification.BigTextStyle;
import android.app.Notification.Builder;
import android.app.Notification.InboxStyle;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.net.Uri;
import android.os.Build.VERSION;
import android.preference.PreferenceManager;
import android.support.v4.app.NotificationCompat.Builder;
import android.text.Html;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.R.bool;
import com.google.android.apps.plus.R.dimen;
import com.google.android.apps.plus.R.drawable;
import com.google.android.apps.plus.R.plurals;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.api.DownloadPhotoOperation;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbDataAction;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.content.EsNotificationData;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.content.PersonData;
import com.google.android.apps.plus.content.PhotoTaggeeData;
import com.google.android.apps.plus.content.PhotoTaggeeData.PhotoTaggee;
import com.google.android.apps.plus.phone.InstantUpload;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.Intents.PhotoViewIntentBuilder;
import com.google.android.apps.plus.phone.Intents.PhotosIntentBuilder;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.ParticipantParcelable;
import com.google.android.apps.plus.util.PrimitiveUtils;
import com.google.api.services.plusi.model.DataAction;
import com.google.api.services.plusi.model.DataActor;
import com.google.api.services.plusi.model.DataImage;
import com.google.api.services.plusi.model.DataItem;
import com.google.api.services.plusi.model.DataPhoto;
import com.google.api.services.plusi.model.EntityPhotosData;
import com.google.api.services.plusi.model.EntityPhotosDataJson;
import com.google.api.services.plusi.model.EntitySquaresData;
import com.google.api.services.plusi.model.EntitySquaresDataJson;
import com.google.api.services.plusi.model.EntitySquaresDataNewModerator;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

public final class AndroidNotification
{
  private static final int[] COMMENT_NOTIFICATION_TYPES = { 2, 3, 14, 25, 26 };
  private static final int[] NOTIFICATION_IDS = { 1, 2, 3, 4, 5 };

  private static String buildNotificationTag(Context paramContext, EsAccount paramEsAccount)
  {
    return paramContext.getPackageName() + ":notifications:" + paramEsAccount.getName();
  }

  public static void cancel(Context paramContext, EsAccount paramEsAccount, int paramInt)
  {
    try
    {
      String str = buildNotificationTag(paramContext, paramEsAccount);
      ((NotificationManager)paramContext.getSystemService("notification")).cancel(str, paramInt);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void cancelAll(Context paramContext, EsAccount paramEsAccount)
  {
    try
    {
      String str = buildNotificationTag(paramContext, paramEsAccount);
      NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
      int[] arrayOfInt = NOTIFICATION_IDS;
      int i = arrayOfInt.length;
      for (int j = 0; j < i; j++)
        localNotificationManager.cancel(str, arrayOfInt[j]);
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public static void cancelFirstTimeFullSizeNotification(Context paramContext, EsAccount paramEsAccount)
  {
    ((NotificationManager)paramContext.getSystemService("notification")).cancel(buildNotificationTag(paramContext, paramEsAccount), 5);
  }

  public static void cancelQuotaNotification(Context paramContext, EsAccount paramEsAccount)
  {
    ((NotificationManager)paramContext.getSystemService("notification")).cancel(buildNotificationTag(paramContext, paramEsAccount), 4);
  }

  private static int countActorsForComment(Map<Integer, List<String>> paramMap)
  {
    HashSet localHashSet = new HashSet();
    int[] arrayOfInt = COMMENT_NOTIFICATION_TYPES;
    int i = arrayOfInt.length;
    for (int j = 0; j < i; j++)
    {
      List localList = (List)paramMap.get(Integer.valueOf(arrayOfInt[j]));
      if (localList != null)
      {
        Iterator localIterator = localList.iterator();
        while (localIterator.hasNext())
          localHashSet.add((String)localIterator.next());
      }
    }
    return localHashSet.size();
  }

  private static AudienceData createAudienceData(List<PhotoTaggeeData.PhotoTaggee> paramList)
  {
    if ((paramList == null) || (paramList.isEmpty()));
    ArrayList localArrayList;
    for (AudienceData localAudienceData = null; ; localAudienceData = new AudienceData(localArrayList, null))
    {
      return localAudienceData;
      localArrayList = new ArrayList();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        PhotoTaggeeData.PhotoTaggee localPhotoTaggee = (PhotoTaggeeData.PhotoTaggee)localIterator.next();
        localArrayList.add(new PersonData(localPhotoTaggee.getId(), localPhotoTaggee.getName(), null));
      }
    }
  }

  private static AudienceData createAudienceDataForYourCircles(Context paramContext, EsAccount paramEsAccount)
  {
    return new AudienceData(EsPeopleData.getCircleData(paramContext, paramEsAccount, 5));
  }

  private static Notification createDigestNotification$78923c81(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor)
  {
    if (hasOnlyHangoutNotifications(paramCursor));
    for (Notification localNotification = null; ; localNotification = null)
    {
      return localNotification;
      if (paramCursor.moveToFirst())
        break;
    }
    long l = 9223372036854775807L;
    int i = 0;
    Notification.InboxStyle localInboxStyle = new Notification.InboxStyle();
    Object localObject = null;
    do
      if (paramCursor.getInt(3) != 8)
      {
        String str2 = paramCursor.getString(4);
        localInboxStyle.addLine(str2);
        if (localObject == null)
          localObject = str2;
        l = Math.min(l, paramCursor.getLong(5) / 1000L);
        i++;
      }
    while (paramCursor.moveToNext());
    int j = i;
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getQuantityString(R.plurals.notifications_ticker_text, j);
    Intent localIntent = Intents.getNotificationsIntent(paramContext, paramEsAccount, paramCursor);
    localIntent.addFlags(335544320);
    localIntent.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
    Notification.Builder localBuilder = new Notification.Builder(paramContext);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
    localInboxStyle.setBigContentTitle(str1);
    localBuilder.setTicker(str1).setContentTitle(str1).setWhen(l).setPriority(0).setNumber(j).setSmallIcon(R.drawable.ic_stat_gplus).setLargeIcon(BitmapFactory.decodeResource(localResources, R.drawable.stat_notify_multiple_gplus)).setContentIntent(localPendingIntent).setDeleteIntent(EsService.getDeleteNotificationsIntent(paramContext, paramEsAccount, 1)).setStyle(localInboxStyle);
    if (!TextUtils.isEmpty(localObject))
      localBuilder.setContentText(localObject);
    if (hasRingtone(paramContext))
      localBuilder.setSound(getRingtone(paramContext));
    while (true)
    {
      localNotification = localBuilder.build();
      break;
      localBuilder.setDefaults(1);
    }
  }

  private static ArrayList<MediaRef> createMediaRefList(String paramString, List<DataPhoto> paramList)
  {
    ArrayList localArrayList = new ArrayList();
    Iterator localIterator = paramList.iterator();
    while (localIterator.hasNext())
    {
      DataPhoto localDataPhoto = (DataPhoto)localIterator.next();
      if ((localDataPhoto != null) && (localDataPhoto.original != null) && (!TextUtils.isEmpty(localDataPhoto.id)) && (!TextUtils.isEmpty(localDataPhoto.original.url)))
        while (true)
        {
          MediaRef.MediaType localMediaType;
          try
          {
            long l = Long.valueOf(localDataPhoto.id).longValue();
            if (localDataPhoto.video == null)
              break label167;
            localMediaType = MediaRef.MediaType.VIDEO;
            localArrayList.add(new MediaRef(paramString, l, localDataPhoto.original.url, null, localMediaType));
          }
          catch (NumberFormatException localNumberFormatException)
          {
            Log.e("AndroidNotification", "Cannot convert " + localDataPhoto.id + " into Long.");
          }
          break;
          label167: if ((localDataPhoto.isPanorama != null) && (localDataPhoto.isPanorama.booleanValue()))
            localMediaType = MediaRef.MediaType.PANORAMA;
          else
            localMediaType = MediaRef.MediaType.IMAGE;
        }
    }
    return localArrayList;
  }

  private static Notification createNotification(Context paramContext, EsAccount paramEsAccount)
  {
    Cursor localCursor = EsNotificationData.getNotificationsToDisplay(paramContext, paramEsAccount);
    int i;
    Resources localResources2;
    int i4;
    String str4;
    String str5;
    String str6;
    String str7;
    String str8;
    int i5;
    String str9;
    List localList2;
    Bitmap localBitmap1;
    String str10;
    Map localMap1;
    String str46;
    List localList7;
    String str14;
    String str47;
    String str48;
    label390: int i54;
    String str49;
    PendingIntent localPendingIntent11;
    label453: int i7;
    Bitmap localBitmap2;
    label545: Notification.Builder localBuilder;
    Object localObject5;
    Object localObject7;
    int i10;
    Object localObject10;
    Object localObject4;
    Object localObject9;
    label634: Object localObject3;
    int i11;
    Object localObject8;
    Object localObject6;
    int i6;
    Object localObject2;
    PendingIntent localPendingIntent6;
    label706: Notification localNotification1;
    if ((localCursor != null) && (localCursor.moveToFirst()))
    {
      int i3;
      long l3;
      PendingIntent localPendingIntent5;
      List localList1;
      ArrayList localArrayList1;
      try
      {
        i = localCursor.getCount();
        if (i != 1)
          break label4394;
        if (!isRunningJellybeanOrLater())
          break label4160;
        localResources2 = paramContext.getResources();
        i3 = localCursor.getInt(3);
        i4 = localCursor.getInt(15);
        str4 = localCursor.getString(1);
        str5 = localCursor.getString(2);
        str6 = localCursor.getString(4);
        str7 = localResources2.getQuantityString(R.plurals.notifications_ticker_text, 1);
        l3 = localCursor.getLong(5) / 1000L;
        str8 = localCursor.getString(10);
        localPendingIntent5 = newViewOneIntent(paramContext, paramEsAccount, localCursor);
        BitmapFactory.decodeResource(localResources2, R.drawable.stat_notify_gplus);
        i5 = R.drawable.ic_stat_gplus;
        str9 = localCursor.getString(21);
        localList1 = DbDataAction.deserializeDataActionList(localCursor.getBlob(6));
        localList2 = DbDataAction.getDataActorList(localList1);
        localArrayList1 = new ArrayList();
        if (localList2 != null)
        {
          Iterator localIterator6 = localList2.iterator();
          while (localIterator6.hasNext())
          {
            DataActor localDataActor9 = (DataActor)localIterator6.next();
            if ((localDataActor9 != null) && (localDataActor9.photoUrl != null))
            {
              Bitmap localBitmap12 = EsMediaCache.obtainAvatar(paramContext, paramEsAccount, localDataActor9.obfuscatedGaiaId, EsAvatarData.uncompressAvatarUrl(localDataActor9.photoUrl), false);
              if (localBitmap12 != null)
                localArrayList1.add(localBitmap12);
            }
          }
        }
      }
      finally
      {
        localCursor.close();
      }
      localBitmap1 = EsMediaCache.obtainAvatarForMultipleUsers(localArrayList1);
      str10 = localCursor.getString(17);
      if (!TextUtils.isEmpty(str10))
        str10 = Html.fromHtml(str10).toString();
      localMap1 = getActorsMap(localList1);
      if ((i3 != 1) || (localList2 == null) || (localList2.isEmpty()))
        break label4825;
      if (localList2.size() == 1)
      {
        str46 = ((DataActor)localList2.get(0)).name;
        localList7 = null;
        str14 = str6;
        str47 = str6;
        str48 = str46;
        i54 = R.drawable.stat_notify_comment;
        str49 = localResources2.getString(R.string.notifications_action_comment);
        Intent localIntent4 = Intents.getPostCommentsActivityIntent(paramContext, paramEsAccount, str8, str4, i3, true, true);
        if ((localIntent4 == null) || (localIntent4.resolveActivity(paramContext.getPackageManager()) == null))
          break label1144;
        localPendingIntent11 = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent4, 0);
        break label4855;
        if (i3 != 11)
          break label4796;
        i7 = R.drawable.ic_community_notify;
        if (i4 == 48)
          break label4789;
        localBitmap2 = EsMediaCache.obtainAvatar(paramContext, paramEsAccount, localCursor.getString(20), EsAvatarData.uncompressAvatarUrl(localCursor.getString(22)), false);
        if (localBitmap2 == null)
          break label4789;
        break label4886;
        if (!EsLog.isLoggable("AndroidNotification", 3))
          break label4996;
        Log.d("AndroidNotification", "Unknown notification type: " + i4);
        break label4996;
        localBuilder = new Notification.Builder(paramContext);
        localBuilder.setTicker(str6).setContentTitle((CharSequence)localObject5).setContentText((CharSequence)localObject7).setWhen(l3).setPriority(0).setSmallIcon(i10).setContentIntent(localPendingIntent5).setDeleteIntent(EsService.getDeleteNotificationsIntent(paramContext, paramEsAccount, 1));
        if (localObject10 == null)
          break label4032;
        localBuilder.setStyle(new Notification.BigPictureStyle().setBigContentTitle((CharSequence)localObject4).setSummaryText((CharSequence)localObject9).bigPicture((Bitmap)localObject10));
        if (localObject3 != null)
          localBuilder.setLargeIcon((Bitmap)localObject3);
        if (i11 != 0)
          localBuilder.addAction(i11, (CharSequence)localObject8, (PendingIntent)localObject6);
        if (i6 != 0)
          localBuilder.addAction(i6, (CharSequence)localObject2, localPendingIntent6);
        if (i3 != 65535)
        {
          if (!hasRingtone(paramContext))
            break label4150;
          localBuilder.setSound(getRingtone(paramContext));
        }
        Notification localNotification2 = localBuilder.build();
        localNotification1 = localNotification2;
      }
    }
    label716: label722: label2266: label2279: label5479: 
    while (true)
    {
      localCursor.close();
      return localNotification1;
      int i49 = 0;
      Iterator localIterator5 = localMap1.values().iterator();
      while (localIterator5.hasNext())
        i49 += ((List)localIterator5.next()).size();
      int i50 = localMap1.keySet().size();
      Log.e("AndroidNotification", "# actors " + i49 + " # of actions " + i50);
      int i51 = countActorsForComment(localMap1);
      int i52;
      if (i50 == 1)
        switch (i4)
        {
        case 4:
        case 6:
        case 7:
        case 8:
        case 9:
        case 10:
        case 11:
        case 12:
        case 13:
        case 17:
        case 18:
        case 19:
        case 22:
        case 23:
        default:
          i52 = R.plurals.notifications_single_post_title_post;
        case 2:
        case 3:
        case 14:
        case 25:
        case 26:
        case 20:
        case 21:
        case 5:
        case 15:
        case 16:
        case 24:
        }
      int i53;
      while (true)
      {
        Object[] arrayOfObject9 = new Object[1];
        arrayOfObject9[0] = Integer.valueOf(i49);
        str46 = localResources2.getQuantityString(i52, i49, arrayOfObject9);
        i53 = localMap1.keySet().size();
        localList7 = getNamesForDisplay(paramContext, localMap1);
        if ((localList7 == null) || (localList7.isEmpty()))
          break label4807;
        if (i53 != 1)
          break label5035;
        str14 = (String)localList7.get(0) + "\n\n" + str10;
        str47 = str10;
        str48 = str46;
        localList7 = null;
        break;
        i52 = R.plurals.notifications_single_post_title_comment;
        continue;
        i52 = R.plurals.notifications_single_post_title_plus_one;
        continue;
        i52 = R.plurals.notifications_single_post_title_reshare;
        continue;
        i52 = R.plurals.notifications_single_post_title_mention;
        continue;
        i52 = R.plurals.notifications_single_post_title_share;
        continue;
        if (i51 > 0)
        {
          i52 = R.plurals.notifications_single_post_title_comment;
          i49 = i51;
        }
        else
        {
          i52 = R.plurals.notification_single_post;
        }
      }
      label1144: PendingIntent localPendingIntent7;
      String str15;
      int i8;
      int i9;
      int i12;
      String str21;
      label1523: String str22;
      int i16;
      int i17;
      int i14;
      String str19;
      Object localObject11;
      label1651: label1659: String str18;
      int i25;
      Bitmap localBitmap4;
      label2033: int i33;
      String str39;
      label2157: int i37;
      String str40;
      label2299: Bitmap localBitmap5;
      label2671: PendingIntent localPendingIntent10;
      label2799: int i24;
      PendingIntent localPendingIntent9;
      String str33;
      label3199: Bitmap localBitmap3;
      Object localObject17;
      Object localObject16;
      String str13;
      String str11;
      String str12;
      String str23;
      boolean bool1;
      label3831: Iterator localIterator2;
      Object localObject12;
      Object localObject13;
      if (EsLog.isLoggable("AndroidNotification", 2))
      {
        Log.v("AndroidNotification", "newPostCommentsActivityPendingIntent returning null");
        break label5056;
        String str42 = EsPeopleData.getCircleId(paramContext, paramEsAccount, localResources2.getString(R.string.friends_circle_name));
        if (!TextUtils.isEmpty(str42))
        {
          ArrayList localArrayList4 = new ArrayList();
          String str43 = paramEsAccount.getGaiaId();
          Iterator localIterator4 = localList2.iterator();
          while (localIterator4.hasNext())
          {
            DataActor localDataActor8 = (DataActor)localIterator4.next();
            String str45 = localDataActor8.obfuscatedGaiaId;
            if ((!TextUtils.isEmpty(str45)) && (!str45.equals(str43)) && (EsPeopleData.getPackedCircleIds(paramContext, paramEsAccount, str45) == null))
              localArrayList4.add(localDataActor8);
          }
          if (localList2.size() > 0)
          {
            int i44 = localArrayList4.size();
            DataActor localDataActor1;
            int i15;
            if (i44 > 0)
            {
              int i46 = R.drawable.stat_notify_adduser;
              if (i44 == 1);
              String str44;
              String[] arrayOfString;
              ParticipantParcelable[] arrayOfParticipantParcelable;
              for (int i47 = R.string.notifications_action_add_to_friends; ; i47 = R.string.notifications_action_add_all_to_friends)
              {
                str44 = localResources2.getString(i47);
                arrayOfString = new String[] { str42 };
                arrayOfParticipantParcelable = new ParticipantParcelable[i44];
                for (int i48 = 0; i48 < i44; i48++)
                {
                  DataActor localDataActor7 = (DataActor)localArrayList4.get(i48);
                  ParticipantParcelable localParticipantParcelable = new ParticipantParcelable(localDataActor7.name, "g:" + localDataActor7.obfuscatedGaiaId);
                  arrayOfParticipantParcelable[i48] = localParticipantParcelable;
                }
              }
              localPendingIntent7 = EsService.createAddPeopleToCirclePendingIntent(paramContext, paramEsAccount, arrayOfParticipantParcelable, arrayOfString, str4);
              str15 = str44;
              i8 = i46;
              i9 = R.drawable.stat_notify_circle;
              if ((localList2 == null) || (localList2.isEmpty()))
                break label4735;
              if (i4 != 39)
                break label5117;
              i12 = 1;
              if (localList2.size() != 1)
                break label1709;
              localDataActor1 = (DataActor)localList2.get(0);
              str21 = localDataActor1.name;
              if (i12 == 0)
                break label1651;
              i15 = R.string.notification_content_added_back_to_circle;
              str22 = localResources2.getString(i15);
              if (i12 == 0)
                break label4720;
              DataActor localDataActor2 = (DataActor)localList2.get(0);
              if (localDataActor2 == null)
                break label5123;
              i16 = 1;
              if (localDataActor2.obfuscatedGaiaId == null)
                break label5129;
              i17 = 1;
              if ((i17 & i16) == 0)
                break label4720;
              i14 = R.drawable.stat_notify_comment;
              str19 = localResources2.getString(R.string.notifications_action_say_hello);
              if (localDataActor1 != null)
                break label1659;
            }
            ArrayList localArrayList2;
            for (AudienceData localAudienceData1 = null; ; localAudienceData1 = new AudienceData(localArrayList2, null))
            {
              Intent localIntent2 = Intents.getPostActivityIntent(paramContext, paramEsAccount, null, localAudienceData1);
              localObject11 = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent2, 0);
              break label5067;
              int i45 = R.drawable.stat_notify_addeduser;
              str15 = localResources2.getString(R.string.notifications_action_added_to_friends);
              i8 = i45;
              localPendingIntent7 = null;
              break;
              i15 = R.string.notification_content_added_to_circle;
              break label1523;
              localArrayList2 = new ArrayList();
              localArrayList2.add(new PersonData(localDataActor1.obfuscatedGaiaId, localDataActor1.name, null));
            }
            if (i12 != 0);
            for (int i13 = R.string.notification_title_added_back_to_circle; ; i13 = R.string.notification_title_added_to_circle)
            {
              Object[] arrayOfObject2 = new Object[1];
              arrayOfObject2[0] = Integer.valueOf(localList2.size());
              String str16 = localResources2.getString(i13, arrayOfObject2);
              String str17 = getActorNamesForDisplay(localList2);
              PendingIntent localPendingIntent8 = localPendingIntent7;
              i14 = i8;
              str18 = str16;
              str19 = str15;
              localObject9 = str17;
              localObject11 = localPendingIntent8;
              break;
            }
            i25 = R.drawable.stat_notify_instant_upload;
            localBitmap4 = BitmapFactory.decodeResource(localResources2, R.drawable.stat_notify_instant_upload);
            String str37 = localCursor.getString(7);
            byte[] arrayOfByte3 = localCursor.getBlob(18);
            EntityPhotosData localEntityPhotosData = (EntityPhotosData)EntityPhotosDataJson.getInstance().fromByteArray(arrayOfByte3);
            String str38 = localCursor.getString(23);
            List localList4 = DbDataAction.deserializeDataActorList(localCursor.getBlob(24));
            Object localObject18;
            int i28;
            int i29;
            Map localMap2;
            int i30;
            int i31;
            ArrayList localArrayList3;
            int i32;
            if (localEntityPhotosData == null)
            {
              localObject18 = null;
              i28 = 0;
              i29 = 0;
              localMap2 = PhotoTaggeeData.createMediaRefUserMap((List)localObject18, localList4, str38);
              i30 = (int)localResources2.getDimension(R.dimen.notification_bigpicture_width);
              i31 = (int)localResources2.getDimension(R.dimen.notification_bigpicture_height);
              localArrayList3 = new ArrayList();
              Iterator localIterator3 = ((ArrayList)localObject18).iterator();
              do
              {
                if (!localIterator3.hasNext())
                  break;
                DownloadPhotoOperation localDownloadPhotoOperation = new DownloadPhotoOperation(paramContext, paramEsAccount, ((MediaRef)localIterator3.next()).getUrl());
                localDownloadPhotoOperation.start();
                byte[] arrayOfByte4 = localDownloadPhotoOperation.getBytes();
                if (arrayOfByte4 != null)
                {
                  Bitmap localBitmap11 = ImageUtils.createRotatedBitmap(paramContext, arrayOfByte4, i30, i31);
                  if (localBitmap11 != null)
                    localArrayList3.add(localBitmap11);
                }
              }
              while (localArrayList3.size() < 3);
              if (localArrayList3 == null)
                break label5135;
              if (!localArrayList3.isEmpty())
                break label2299;
              break label5135;
              if (!localArrayList3.isEmpty())
              {
                int i40 = (int)localResources2.getDimension(R.dimen.notification_large_icon_size);
                localBitmap4 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(0), i40, i40);
              }
              i32 = i29 + i28;
              localObject5 = localResources2.getQuantityString(R.plurals.notifications_instant_upload_title, i32);
              if (localList4 != null)
                break label2671;
              i33 = 0;
              break label5141;
              int i39 = R.string.notifications_instant_upload_more_than_two_taggee_names;
              Object[] arrayOfObject8 = new Object[2];
              arrayOfObject8[0] = ((DataActor)localList4.get(0)).name;
              arrayOfObject8[1] = Integer.valueOf(i33 - 1);
              str39 = localResources2.getString(i39, arrayOfObject8);
              if (!TextUtils.isEmpty(str39))
                break label2799;
              if (i32 != 1)
                break label2763;
            }
            int i38;
            Object[] arrayOfObject7;
            for (localObject7 = null; ; localObject7 = localResources2.getQuantityString(i38, i32, arrayOfObject7))
            {
              i37 = R.drawable.stat_notify_reshare;
              str40 = localResources2.getString(R.string.post_share_button_text);
              if (localObject18 == null)
                break label5168;
              if (!((ArrayList)localObject18).isEmpty())
                break label2977;
              break label5168;
              int i26;
              if (localEntityPhotosData.numPhotos == null)
              {
                i26 = 0;
                if (localEntityPhotosData.numVideos != null)
                  break label2266;
              }
              List localList5;
              for (int i27 = 0; ; i27 = localEntityPhotosData.numVideos.intValue())
              {
                localList5 = localEntityPhotosData.photo;
                if (localList5 != null)
                  break label2279;
                i28 = i27;
                i29 = i26;
                localObject18 = null;
                break;
                i26 = localEntityPhotosData.numPhotos.intValue();
                break label2216;
              }
              localObject18 = createMediaRefList(str37, localList5);
              i28 = i27;
              i29 = i26;
              break;
              int i41 = localArrayList3.size();
              localBitmap5 = Bitmap.createBitmap(i30, i31, Bitmap.Config.ARGB_8888);
              Canvas localCanvas = new Canvas(localBitmap5);
              Paint localPaint = new Paint();
              localPaint.setColor(-16777216);
              localPaint.setStrokeWidth(4.0F);
              if (i41 == 1)
              {
                localBitmap5 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(0), i30, i31);
                break label2033;
              }
              if (i41 == 2)
              {
                Bitmap localBitmap9 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(0), i30 / 2, i31);
                Bitmap localBitmap10 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(1), i30 / 2, i31);
                localCanvas.drawBitmap(localBitmap9, 0.0F, 0.0F, localPaint);
                localCanvas.drawBitmap(localBitmap10, i30 / 2, 0.0F, localPaint);
                localCanvas.drawLine(i30 / 2, 0.0F, i30 / 2, i31, localPaint);
                break label2033;
              }
              if (i41 < 3)
                break label2033;
              int i42 = i30 / 3;
              int i43 = i31 / 2;
              Bitmap localBitmap6 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(0), i30 - i42, i31);
              Bitmap localBitmap7 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(1), i42, i43);
              Bitmap localBitmap8 = EsMediaCache.cropPhoto$5d96f1cd((Bitmap)localArrayList3.get(2), i42, i43);
              localCanvas.drawBitmap(localBitmap6, 0.0F, 0.0F, localPaint);
              localCanvas.drawBitmap(localBitmap7, i30 - i42, 0.0F, localPaint);
              localCanvas.drawBitmap(localBitmap8, i30 - i42, i43, localPaint);
              localCanvas.drawLine(i30 - i42, 0.0F, i30 - i42, i31, localPaint);
              localCanvas.drawLine(i30 - i42, i43, i30, i43, localPaint);
              break label2033;
              i33 = localList4.size();
              break label5141;
              str39 = ((DataActor)localList4.get(0)).name;
              break label2157;
              int i34 = R.string.notifications_instant_upload_two_taggee_names;
              Object[] arrayOfObject6 = new Object[2];
              arrayOfObject6[0] = ((DataActor)localList4.get(0)).name;
              arrayOfObject6[1] = ((DataActor)localList4.get(1)).name;
              str39 = localResources2.getString(i34, arrayOfObject6);
              break label2157;
              i38 = R.plurals.notifications_instant_upload_text;
              arrayOfObject7 = new Object[1];
              arrayOfObject7[0] = Integer.valueOf(i32);
            }
            StringBuilder localStringBuilder = new StringBuilder().append(localResources2.getQuantityString(R.plurals.notifications_instant_upload_known_faces_text, i32, new Object[] { str39 })).append(" ");
            int i35;
            DataActor localDataActor6;
            int i36;
            if (localList4 == null)
            {
              i35 = 0;
              if (i35 != 1)
                break label2969;
              localDataActor6 = (DataActor)localList4.get(0);
              if ((localDataActor6 != null) && (!TextUtils.isEmpty(localDataActor6.gender)))
                break label2916;
              i36 = R.string.notifications_instant_upload_share_with_them;
            }
            while (true)
            {
              localObject7 = localResources2.getString(i36);
              break;
              i35 = localList4.size();
              break label2844;
              String str41 = localDataActor6.gender;
              if (TextUtils.equals("male", str41))
              {
                i36 = R.string.notifications_instant_upload_share_with_him;
              }
              else if (TextUtils.equals("female", str41))
              {
                i36 = R.string.notifications_instant_upload_share_with_her;
              }
              else
              {
                i36 = R.string.notifications_instant_upload_share_with_them;
                continue;
                i36 = R.string.notifications_instant_upload_share_with_them;
              }
            }
            List localList6;
            AudienceData localAudienceData3;
            if (((ArrayList)localObject18).size() == 1)
            {
              localList6 = (List)localMap2.get(((ArrayList)localObject18).get(0));
              if ((localList6 == null) || (localList6.isEmpty()))
                localAudienceData3 = createAudienceDataForYourCircles(paramContext, paramEsAccount);
            }
            AudienceData localAudienceData2;
            for (Intent localIntent3 = Intents.getPostActivityIntent(paramContext, paramEsAccount, (ArrayList)localObject18, localAudienceData3); ; localIntent3 = getPhotosSelectionActivityIntent(paramContext, paramEsAccount, str4, (ArrayList)localObject18, localMap2, localAudienceData2))
            {
              if ((localIntent3 == null) || (localIntent3.resolveActivity(paramContext.getPackageManager()) == null))
                break label3136;
              localIntent3.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
              localIntent3.putExtra("notif_id", str4);
              localIntent3.putExtra("coalescing_id", str5);
              localPendingIntent10 = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent3, 0);
              break label5171;
              localAudienceData3 = createAudienceData(localList6);
              break;
              localAudienceData2 = createAudienceDataForYourCircles(paramContext, paramEsAccount);
            }
            if (!EsLog.isLoggable("AndroidNotification", 2))
              break label5212;
            Log.v("AndroidNotification", "newInstantUploadAlbumIntent returning null");
            break label5212;
            if (!EsPostsData.isActivityPlusOnedByViewer(paramContext, paramEsAccount, str8))
            {
              i24 = R.drawable.stat_notify_plusone;
              String str36 = localResources2.getString(R.string.notifications_action_plusone_post);
              localPendingIntent9 = EsService.getCreatePostPlusOneIntent(paramContext, paramEsAccount, str8, str4);
              str33 = str36;
              localBitmap3 = EsPostsData.getActivityImageData(paramContext, paramEsAccount, str8);
              if ((localBitmap3 != null) || (!EsLog.isLoggable("AndroidNotification", 3)))
                break label5218;
              Log.d("AndroidNotification", "failed to decode media object for " + str8);
              break label5218;
              if (localList2.isEmpty())
                break label4996;
              DataActor localDataActor5 = (DataActor)localList2.get(0);
              if (!TextUtils.isEmpty(localDataActor5.name))
              {
                localObject17 = localDataActor5.name;
                if (!TextUtils.isEmpty(str9))
                {
                  localObject16 = localResources2.getString(R.string.notification_square_invite, new Object[] { str9 });
                  break label5270;
                  if (TextUtils.isEmpty(str9))
                    break label4996;
                  String str31 = localResources2.getString(R.string.notification_square_membership_approved);
                  i10 = i7;
                  localObject3 = localBitmap2;
                  localObject9 = str14;
                  localObject7 = str31;
                  String str32 = str13;
                  localObject5 = str9;
                  localObject4 = str32;
                  localObject10 = null;
                  localObject6 = null;
                  localObject8 = null;
                  i11 = 0;
                  break label545;
                  if (TextUtils.isEmpty(str9))
                    break label4996;
                  byte[] arrayOfByte2 = localCursor.getBlob(19);
                  EntitySquaresData localEntitySquaresData2 = (EntitySquaresData)EntitySquaresDataJson.getInstance().fromByteArray(arrayOfByte2);
                  if (localEntitySquaresData2 == null)
                    break label5309;
                  int i20 = EsNotificationData.getNumSquarePosts(localEntitySquaresData2);
                  int i21 = EsNotificationData.getUnreadSquarePosts(localEntitySquaresData2);
                  if (EsLog.isLoggable("AndroidNotification", 4))
                    Log.i("AndroidNotification", "Square subscription numPosts=" + i20 + " numUnread=" + i21);
                  if (i20 > 1)
                  {
                    if (i21 > 0)
                    {
                      int i23 = R.plurals.notification_square_multi_post_unread;
                      Object[] arrayOfObject5 = new Object[1];
                      arrayOfObject5[0] = Integer.valueOf(i21);
                      String str29 = localResources2.getQuantityString(i23, i21, arrayOfObject5);
                      i10 = i7;
                      localObject3 = localBitmap2;
                      localObject9 = str14;
                      localObject7 = str29;
                      String str30 = str13;
                      localObject5 = str9;
                      localObject4 = str30;
                      localObject10 = null;
                      localObject6 = null;
                      localObject8 = null;
                      i11 = 0;
                      break label545;
                    }
                    String str27 = localResources2.getQuantityString(R.plurals.notification_square_multi_post_read, i20);
                    i10 = i7;
                    localObject3 = localBitmap2;
                    localObject9 = str14;
                    localObject7 = str27;
                    String str28 = str13;
                    localObject5 = str9;
                    localObject4 = str28;
                    localObject10 = null;
                    localObject6 = null;
                    localObject8 = null;
                    i11 = 0;
                    break label545;
                  }
                  if (localList2.size() <= 0)
                    break label5309;
                  DataActor localDataActor4 = (DataActor)localList2.get(0);
                  if (TextUtils.isEmpty(localDataActor4.name))
                    break label5309;
                  int i22 = R.string.notification_square_single_post_share;
                  Object[] arrayOfObject4 = new Object[1];
                  arrayOfObject4[0] = localDataActor4.name;
                  str11 = localResources2.getString(i22, arrayOfObject4);
                  str12 = str9;
                  break label5309;
                  if ((!TextUtils.isEmpty(str9)) && (!localList2.isEmpty()))
                  {
                    byte[] arrayOfByte1 = localCursor.getBlob(19);
                    EntitySquaresData localEntitySquaresData1 = (EntitySquaresData)EntitySquaresDataJson.getInstance().fromByteArray(arrayOfByte1);
                    if ((localEntitySquaresData1 != null) && (localEntitySquaresData1.newModerator != null) && (localEntitySquaresData1.newModerator.size() > 0))
                    {
                      EntitySquaresDataNewModerator localEntitySquaresDataNewModerator = (EntitySquaresDataNewModerator)localEntitySquaresData1.newModerator.get(0);
                      if ((localEntitySquaresDataNewModerator.newModeratorOid != null) && (localEntitySquaresDataNewModerator.newModeratorOid.size() > 0))
                      {
                        String str26 = (String)localEntitySquaresDataNewModerator.newModeratorOid.get(0);
                        boolean bool2 = PrimitiveUtils.safeBoolean(localEntitySquaresDataNewModerator.toOwner);
                        str23 = str26;
                        bool1 = bool2;
                        if (TextUtils.isEmpty(str23))
                          break label4996;
                        if (paramEsAccount.isMyGaiaId(str23))
                        {
                          if (bool1);
                          for (int i19 = R.string.notification_square_promoted_to_owner; ; i19 = R.string.notification_square_promoted_to_mod)
                          {
                            String str24 = localResources2.getString(i19);
                            i10 = i7;
                            localObject3 = localBitmap2;
                            localObject9 = str14;
                            localObject7 = str24;
                            String str25 = str13;
                            localObject5 = str9;
                            localObject4 = str25;
                            localObject10 = null;
                            localObject6 = null;
                            localObject8 = null;
                            i11 = 0;
                            break;
                          }
                        }
                        localIterator2 = localList2.iterator();
                        localObject12 = str11;
                        localObject13 = str12;
                      }
                    }
                  }
                }
              }
            }
          }
        }
      }
      while (true)
      {
        if (!localIterator2.hasNext())
          break label5359;
        DataActor localDataActor3 = (DataActor)localIterator2.next();
        List localList3;
        if ((str23.equals(localDataActor3.obfuscatedGaiaId)) && (!TextUtils.isEmpty(localDataActor3.name)))
        {
          if (bool1);
          for (int i18 = R.string.notification_square_new_owner; ; i18 = R.string.notification_square_new_moderator)
          {
            Object[] arrayOfObject3 = new Object[1];
            arrayOfObject3[0] = localDataActor3.name;
            localObject14 = localResources2.getString(i18, arrayOfObject3);
            localObject15 = str9;
            break;
          }
          if (localObject9 == null)
            break label634;
          if (localList3 == null)
          {
            localBuilder.setStyle(new Notification.BigTextStyle().setBigContentTitle((CharSequence)localObject4).bigText((CharSequence)localObject9));
            break label634;
          }
          Notification.InboxStyle localInboxStyle = new Notification.InboxStyle(localBuilder).setBigContentTitle((CharSequence)localObject4);
          Iterator localIterator1 = localList3.iterator();
          while (localIterator1.hasNext())
            localInboxStyle.addLine((String)localIterator1.next());
          if (TextUtils.isEmpty((CharSequence)localObject9))
            break label634;
          localInboxStyle.addLine(" ");
          localInboxStyle.addLine((CharSequence)localObject9);
          break label634;
          localBuilder.setDefaults(1);
          break label706;
          int n = localCursor.getInt(3);
          if (n == 8)
          {
            localNotification1 = null;
            break label5476;
          }
          int i1;
          String str3;
          switch (localCursor.getInt(15))
          {
          default:
            i1 = R.drawable.ic_stat_gplus;
            str3 = localCursor.getString(4);
            long l2 = System.currentTimeMillis();
            localNotification1 = new Notification(i1, str3, l2);
            if (n != 65535)
            {
              if (!hasRingtone(paramContext))
                break label4377;
              Uri localUri2 = getRingtone(paramContext);
              localNotification1.sound = localUri2;
            }
            break;
          case 6:
          case 39:
          case 18:
          case 2:
          case 15:
          case 16:
          case 24:
          }
          while (true)
          {
            PendingIntent localPendingIntent3 = newViewOneIntent(paramContext, paramEsAccount, localCursor);
            localNotification1.setLatestEventInfo(paramContext, str3, "", localPendingIntent3);
            PendingIntent localPendingIntent4 = EsService.getDeleteNotificationsIntent(paramContext, paramEsAccount, 1);
            localNotification1.deleteIntent = localPendingIntent4;
            break label5476;
            i1 = R.drawable.ic_stat_circle;
            break;
            i1 = R.drawable.ic_stat_instant_upload;
            break;
            i1 = R.drawable.ic_stat_post;
            break;
            int i2 = 0x1 | localNotification1.defaults;
            localNotification1.defaults = i2;
          }
          if (i > 1)
          {
            if (isRunningJellybeanOrLater())
            {
              localNotification1 = createDigestNotification$78923c81(paramContext, paramEsAccount, localCursor);
              break label716;
            }
            if (hasOnlyHangoutNotifications(localCursor))
            {
              localNotification1 = null;
              break label5479;
            }
            Resources localResources1 = paramContext.getResources();
            String str1 = localResources1.getQuantityString(R.plurals.notifications_ticker_text, i);
            long l1 = System.currentTimeMillis();
            int j = R.drawable.ic_stat_gplus;
            localNotification1 = new Notification(j, str1, l1);
            int k = R.plurals.notifications_content_text;
            Object[] arrayOfObject1 = new Object[2];
            arrayOfObject1[0] = paramEsAccount.getName();
            arrayOfObject1[1] = Integer.valueOf(i);
            String str2 = localResources1.getQuantityString(k, i, arrayOfObject1);
            Intent localIntent1 = Intents.getNotificationsIntent(paramContext, paramEsAccount, localCursor);
            localIntent1.addFlags(335544320);
            localIntent1.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
            if (hasRingtone(paramContext))
            {
              Uri localUri1 = getRingtone(paramContext);
              localNotification1.sound = localUri1;
            }
            while (true)
            {
              PendingIntent localPendingIntent1 = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent1, 0);
              localNotification1.setLatestEventInfo(paramContext, str1, str2, localPendingIntent1);
              PendingIntent localPendingIntent2 = EsService.getDeleteNotificationsIntent(paramContext, paramEsAccount, 1);
              localNotification1.deleteIntent = localPendingIntent2;
              break;
              int m = 0x1 | localNotification1.defaults;
              localNotification1.defaults = m;
            }
          }
          localNotification1 = null;
          break label716;
          localNotification1 = null;
          break label722;
        }
        Object localObject14 = localObject12;
        Object localObject15 = localObject13;
        break label5348;
        str23 = null;
        bool1 = false;
        break label3831;
        localObject16 = str11;
        break label5270;
        localObject16 = str11;
        localObject17 = str12;
        break label5270;
        label4720: label4735: 
        do
        {
          localObject10 = localBitmap3;
          localObject7 = str11;
          i10 = i7;
          localObject8 = str33;
          localObject6 = localPendingIntent9;
          localObject9 = str14;
          localObject3 = localBitmap2;
          String str34 = str13;
          localObject5 = str12;
          i11 = i24;
          localObject4 = str34;
          break label545;
          localPendingIntent9 = null;
          str33 = null;
          i24 = 0;
          break label3199;
          localObject11 = localPendingIntent7;
          str19 = str15;
          i14 = i8;
          break label5067;
          i10 = i9;
          localObject3 = localBitmap2;
          localObject4 = str13;
          localObject5 = str12;
          i11 = i8;
          localObject6 = localPendingIntent7;
          localObject7 = str11;
          localObject8 = str15;
          localObject9 = str14;
          localObject10 = null;
          break label545;
          localPendingIntent7 = null;
          str15 = null;
          i8 = 0;
          break label1452;
          localBitmap2 = localBitmap1;
          break label4886;
          i7 = i5;
          localBitmap2 = localBitmap1;
          break label4886;
          str14 = str10;
          str47 = str10;
          str48 = str46;
          localList7 = null;
          break;
          str11 = str6;
          str12 = str7;
          str13 = str6;
          str14 = null;
          localList3 = null;
          localPendingIntent6 = null;
          localObject2 = null;
          i6 = 0;
          break label453;
          while (true)
          {
            str11 = str47;
            localObject2 = str49;
            str13 = str46;
            localPendingIntent6 = localPendingIntent11;
            localList3 = localList7;
            str12 = str48;
            i6 = i54;
            break label453;
            switch (i4)
            {
            default:
            case 6:
            case 18:
            case 16:
            case 24:
            case 48:
            case 51:
            case 49:
            case 65:
            case 39:
              i10 = i7;
              localObject3 = localBitmap2;
              localObject9 = str14;
              localObject4 = str13;
              localObject7 = str11;
              localObject5 = str12;
              localObject10 = null;
              localObject6 = null;
              localObject8 = null;
              i11 = 0;
              break label545;
              if (i53 <= 1)
                break label4807;
              str14 = str10;
              str47 = str10;
              str48 = str46;
              break label390;
              localPendingIntent11 = null;
            case 33:
            case 2:
            case 15:
            }
          }
          localNotification1 = null;
          break label716;
          localObject9 = str22;
          str18 = str21;
          localObject8 = str19;
          i11 = i14;
          localObject5 = str18;
          localObject3 = localBitmap2;
          localObject7 = localObject9;
          String str20 = str18;
          localObject6 = localObject11;
          i10 = i9;
          localObject4 = str20;
          localObject10 = null;
          break label545;
          i12 = 0;
          break label1482;
          i16 = 0;
          break label1558;
          i17 = 0;
          break label1569;
          localBitmap5 = null;
          break label2033;
          switch (i33)
          {
          default:
          case 1:
          case 2:
          case 0:
          }
          for (localPendingIntent10 = null; ; localPendingIntent10 = null)
          {
            localObject9 = localObject7;
            localObject6 = localPendingIntent10;
            localObject8 = str40;
            i11 = i37;
            localObject4 = localObject5;
            i10 = i25;
            localObject3 = localBitmap4;
            localObject10 = localBitmap5;
            break;
            str39 = null;
            break label2157;
          }
        }
        while (str14 != null);
        label4855: label5117: localObject10 = localBitmap3;
        localObject7 = str11;
        i10 = i7;
        localObject8 = str33;
        localObject6 = localPendingIntent9;
        localObject9 = str6;
        localObject3 = localBitmap2;
        String str35 = str13;
        localObject5 = str12;
        i11 = i24;
        localObject4 = str35;
        break label545;
        localObject9 = str14;
        localObject4 = str13;
        localObject7 = localObject16;
        localObject5 = localObject17;
        i10 = i7;
        localObject3 = localBitmap2;
        localObject10 = null;
        localObject6 = null;
        localObject8 = null;
        i11 = 0;
        break label545;
        i10 = i7;
        localObject3 = localBitmap2;
        localObject9 = str14;
        localObject4 = str13;
        localObject7 = str11;
        localObject5 = str12;
        localObject10 = null;
        localObject6 = null;
        localObject8 = null;
        i11 = 0;
        break label545;
        localObject13 = localObject15;
        localObject12 = localObject14;
      }
      label5359: i10 = i7;
      localObject9 = str14;
      localObject4 = str13;
      localObject5 = localObject13;
      localObject7 = localObject12;
      localObject3 = localBitmap2;
      localObject10 = null;
      localObject6 = null;
      localObject8 = null;
      i11 = 0;
      break label545;
      i10 = i7;
      localObject3 = localBitmap2;
      localObject9 = str14;
      localObject4 = str13;
      localObject7 = str11;
      localObject5 = str12;
      localObject10 = null;
      localObject6 = null;
      localObject8 = null;
      i11 = 0;
      break label545;
      i10 = i7;
      localObject3 = localBitmap2;
      localObject9 = str14;
      localObject4 = str13;
      localObject7 = str11;
      localObject5 = str12;
      localObject10 = null;
      localObject6 = null;
      localObject8 = null;
      i11 = 0;
      break label545;
    }
  }

  private static String getActorNamesForDisplay(List<DataActor> paramList)
  {
    if (paramList == null);
    StringBuilder localStringBuilder;
    for (String str = ""; ; str = localStringBuilder.toString())
    {
      return str;
      localStringBuilder = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        DataActor localDataActor = (DataActor)localIterator.next();
        if ((localDataActor != null) && (!TextUtils.isEmpty(localDataActor.name)))
        {
          if (localStringBuilder.length() > 0)
            localStringBuilder.append(", ");
          localStringBuilder.append(localDataActor.name);
        }
      }
    }
  }

  private static Map<Integer, List<String>> getActorsMap(List<DataAction> paramList)
  {
    HashMap localHashMap = new HashMap();
    if (paramList != null)
    {
      Iterator localIterator1 = paramList.iterator();
      while (localIterator1.hasNext())
      {
        DataAction localDataAction = (DataAction)localIterator1.next();
        if (localDataAction != null)
        {
          Iterator localIterator2 = localDataAction.item.iterator();
          while (localIterator2.hasNext())
          {
            DataItem localDataItem = (DataItem)localIterator2.next();
            int i = EsNotificationData.getNotificationType(localDataItem.notificationType);
            if (localDataItem.actor != null)
            {
              Object localObject = (List)localHashMap.get(Integer.valueOf(i));
              if (localObject == null)
              {
                localObject = new ArrayList();
                localHashMap.put(Integer.valueOf(i), localObject);
              }
              ((List)localObject).add(localDataItem.actor.name);
            }
          }
        }
      }
    }
    return localHashMap;
  }

  private static String getNamesForDisplay(List<String> paramList)
  {
    if (paramList == null);
    StringBuilder localStringBuilder;
    for (String str1 = ""; ; str1 = localStringBuilder.toString())
    {
      return str1;
      localStringBuilder = new StringBuilder();
      Iterator localIterator = paramList.iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        if (!TextUtils.isEmpty(str2))
        {
          if (localStringBuilder.length() > 0)
            localStringBuilder.append(", ");
          localStringBuilder.append(str2);
        }
      }
    }
  }

  private static List<String> getNamesForDisplay(Context paramContext, Map<Integer, List<String>> paramMap)
  {
    ArrayList localArrayList = new ArrayList();
    Set localSet = paramMap.keySet();
    if (localSet.size() == 1)
    {
      Iterator localIterator2 = localSet.iterator();
      if (localIterator2.hasNext())
        localArrayList.add(getNamesForDisplay((List)paramMap.get(Integer.valueOf(((Integer)localIterator2.next()).intValue()))));
    }
    Iterator localIterator1;
    do
    {
      return localArrayList;
      localIterator1 = paramMap.keySet().iterator();
    }
    while (!localIterator1.hasNext());
    int i = ((Integer)localIterator1.next()).intValue();
    int j;
    switch (i)
    {
    case 4:
    case 6:
    case 7:
    case 8:
    case 9:
    case 10:
    case 11:
    case 12:
    case 13:
    case 17:
    case 18:
    case 19:
    case 22:
    case 23:
    default:
      j = R.string.notifications_single_post_action_post;
    case 2:
    case 3:
    case 14:
    case 25:
    case 26:
    case 20:
    case 21:
    case 5:
    case 15:
    case 16:
    case 24:
    }
    while (true)
    {
      localArrayList.add(paramContext.getString(j) + " " + getNamesForDisplay((List)paramMap.get(Integer.valueOf(i))));
      break;
      j = R.string.notifications_single_post_action_comment;
      continue;
      j = R.string.notifications_single_post_action_plus_one;
      continue;
      j = R.string.notifications_single_post_action_reshare;
      continue;
      j = R.string.notifications_single_post_action_mention;
      continue;
      j = R.string.notifications_single_post_action_share;
    }
  }

  private static Intent getPhotosSelectionActivityIntent(Context paramContext, EsAccount paramEsAccount, String paramString, ArrayList<MediaRef> paramArrayList, Map<MediaRef, List<PhotoTaggeeData.PhotoTaggee>> paramMap, AudienceData paramAudienceData)
  {
    if ((paramArrayList == null) || (paramMap == null));
    MediaRef[] arrayOfMediaRef;
    for (Intent localIntent = null; ; localIntent = Intents.newPhotosSelectionActivityIntentBuilder(paramContext).setAccount(paramEsAccount).setGaiaId(paramEsAccount.getGaiaId()).setMediaRefs(arrayOfMediaRef).setMediaRefUserMap(paramMap).setAudience(paramAudienceData).setNotificationId(paramString).build())
    {
      return localIntent;
      arrayOfMediaRef = new MediaRef[paramArrayList.size()];
      paramArrayList.toArray(arrayOfMediaRef);
    }
  }

  public static Uri getRingtone(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getString(R.string.notifications_preference_ringtone_key);
    String str2 = localResources.getString(R.string.notifications_preference_ringtone_default_value);
    return Uri.parse(PreferenceManager.getDefaultSharedPreferences(paramContext).getString(str1, str2));
  }

  private static boolean hasOnlyHangoutNotifications(Cursor paramCursor)
  {
    boolean bool = true;
    while (paramCursor.moveToNext())
      if (paramCursor.getInt(3) != 8)
        bool = false;
    return bool;
  }

  public static boolean hasRingtone(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str1 = localResources.getString(R.string.notifications_preference_ringtone_key);
    String str2 = localResources.getString(R.string.notifications_preference_ringtone_default_value);
    if (!PreferenceManager.getDefaultSharedPreferences(paramContext).getString(str1, str2).equals(str2));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static boolean isRunningJellybeanOrLater()
  {
    if (Build.VERSION.SDK_INT >= 16);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static Intent newViewNotificationIntent(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor)
  {
    int i = paramCursor.getInt(3);
    Object localObject1;
    if (i == 65535)
      localObject1 = null;
    label155: label1055: label1070: 
    while (true)
    {
      return localObject1;
      int j = paramCursor.getInt(15);
      String str1 = paramCursor.getString(4);
      String str2 = paramContext.getString(R.string.notification_photo_deleted);
      String str3 = paramContext.getString(R.string.notification_event_deleted);
      String str4 = paramContext.getString(R.string.notification_post_deleted);
      if ((TextUtils.isEmpty(str1)) || (TextUtils.equals(str1, str2)) || (TextUtils.equals(str1, str3)) || (TextUtils.equals(str1, str4)))
      {
        localObject1 = null;
      }
      else
      {
        String str5 = paramCursor.getString(1);
        String str6 = paramCursor.getString(13);
        String str7 = paramCursor.getString(14);
        int k;
        if (paramCursor.getInt(12) == 1)
        {
          k = 1;
          localObject1 = null;
          if (k != 0)
            localObject1 = Intents.getHostedEventIntent(paramContext, paramEsAccount, str6, j, str7, null, str5, null);
          if (localObject1 == null)
            switch (i)
            {
            case 4:
            case 5:
            case 6:
            case 7:
            case 9:
            case 10:
            default:
            case 1:
            case 8:
            case 2:
            case 3:
            case 11:
            }
        }
        while (true)
        {
          if (localObject1 == null)
            break label1070;
          ((Intent)localObject1).putExtra("notif_id", str5);
          boolean bool1;
          ArrayList localArrayList3;
          if (paramCursor.getInt(11) != 0)
          {
            bool1 = true;
            ((Intent)localObject1).putExtra("com.google.plus.analytics.intent.extra.NOTIFICATION_READ", bool1);
            ArrayList localArrayList1 = new ArrayList(1);
            localArrayList1.add(Integer.valueOf(j));
            ((Intent)localObject1).putExtra("notif_types", localArrayList1);
            String str8 = paramCursor.getString(2);
            ArrayList localArrayList2 = new ArrayList(1);
            localArrayList2.add(str8);
            ((Intent)localObject1).putExtra("coalescing_codes", localArrayList2);
            String str9 = paramContext.getPackageName();
            ((Intent)localObject1).setPackage(str9);
            ((Intent)localObject1).addFlags(335544320);
            break;
            k = 0;
            break label155;
            String str17 = paramCursor.getString(10);
            if (str17 == null)
              continue;
            localObject1 = Intents.getPostCommentsActivityIntent(paramContext, paramEsAccount, str17, str5, i, true, false);
            continue;
            byte[] arrayOfByte3 = paramCursor.getBlob(6);
            if (arrayOfByte3 == null)
              continue;
            List localList3 = DbDataAction.getDataActorList(DbDataAction.deserializeDataActionList(arrayOfByte3));
            String str16 = paramEsAccount.getGaiaId();
            localArrayList3 = new ArrayList(localList3.size());
            Iterator localIterator = localList3.iterator();
            while (localIterator.hasNext())
            {
              DataActor localDataActor = (DataActor)localIterator.next();
              if (!str16.equals(localDataActor.obfuscatedGaiaId))
                localArrayList3.add(localDataActor);
            }
          }
          try
          {
            byte[] arrayOfByte4 = DbDataAction.serializeDataActorList(localArrayList3);
            if (!localArrayList3.isEmpty())
            {
              int i1 = localArrayList3.size();
              if (i1 == 1)
              {
                localObject1 = Intents.getProfileActivityByGaiaIdIntent(paramContext, paramEsAccount, ((DataActor)localArrayList3.get(0)).obfuscatedGaiaId, str5);
              }
              else if (i1 > 1)
              {
                Intent localIntent = Intents.getAddedToCircleActivityIntent(paramContext, paramEsAccount, arrayOfByte4, str5);
                localObject1 = localIntent;
                continue;
                if (j == 18)
                {
                  String str14 = paramCursor.getString(7);
                  byte[] arrayOfByte2 = paramCursor.getBlob(18);
                  EntityPhotosData localEntityPhotosData = (EntityPhotosData)EntityPhotosDataJson.getInstance().fromByteArray(arrayOfByte2);
                  String str15 = paramCursor.getString(23);
                  List localList1 = DbDataAction.deserializeDataActorList(paramCursor.getBlob(24));
                  List localList2;
                  Object localObject2;
                  if ((localEntityPhotosData != null) && (localEntityPhotosData.photo != null))
                  {
                    localList2 = localEntityPhotosData.photo;
                    if (localList2 == null)
                      localObject2 = null;
                  }
                  Map localMap;
                  AudienceData localAudienceData;
                  while (true)
                  {
                    localMap = PhotoTaggeeData.createMediaRefUserMap((List)localObject2, localList1, str15);
                    localAudienceData = createAudienceDataForYourCircles(paramContext, paramEsAccount);
                    if (localObject2 == null)
                      break;
                    if (((ArrayList)localObject2).size() != 1)
                      break label779;
                    createAudienceData((List)localMap.get(((ArrayList)localObject2).get(0)));
                    localObject1 = Intents.getPostActivityIntent(paramContext, paramEsAccount, (ArrayList)localObject2, localAudienceData);
                    break;
                    localObject2 = createMediaRefList(str14, localList2);
                    continue;
                    localObject2 = null;
                  }
                  localObject1 = getPhotosSelectionActivityIntent(paramContext, paramEsAccount, str5, (ArrayList)localObject2, localMap, localAudienceData);
                }
                else
                {
                  String str12 = paramCursor.getString(8);
                  String str13 = paramCursor.getString(7);
                  long l = paramCursor.getLong(9);
                  if (l != 0L)
                  {
                    MediaRef localMediaRef = new MediaRef(str13, l, null, null, MediaRef.MediaType.IMAGE);
                    Intents.PhotoViewIntentBuilder localPhotoViewIntentBuilder = Intents.newPhotoViewActivityIntentBuilder(paramContext);
                    localPhotoViewIntentBuilder.setAccount(paramEsAccount).setGaiaId(str13).setAlbumId(str12).setPhotoRef(localMediaRef).setNotificationId(str5).setForceLoadId(Long.valueOf(l));
                    if (!TextUtils.isEmpty(paramCursor.getString(20)))
                      localPhotoViewIntentBuilder.setDisableComments(Boolean.valueOf(true));
                    localObject1 = localPhotoViewIntentBuilder.build();
                    continue;
                    EntitySquaresData localEntitySquaresData;
                    if (j == 49)
                    {
                      byte[] arrayOfByte1 = paramCursor.getBlob(19);
                      localEntitySquaresData = (EntitySquaresData)EntitySquaresDataJson.getInstance().fromByteArray(arrayOfByte1);
                      int m = EsNotificationData.getNumSquarePosts(localEntitySquaresData);
                      int n = EsNotificationData.getUnreadSquarePosts(localEntitySquaresData);
                      if ((m == 1) || (n == 1))
                        if (n != 1)
                          break label1055;
                    }
                    for (boolean bool2 = true; ; bool2 = false)
                    {
                      String str11 = EsNotificationData.getSquarePostActivityId(localEntitySquaresData, bool2);
                      if (str11 != null)
                        localObject1 = Intents.getPostCommentsActivityIntent(paramContext, paramEsAccount, str11, str5, i, true, false);
                      if (localObject1 != null)
                        break;
                      String str10 = paramCursor.getString(20);
                      if (str10 == null)
                        break;
                      localObject1 = Intents.getSquareStreamActivityIntent(paramContext, paramEsAccount, str10, null, str5);
                      break;
                    }
                    bool1 = false;
                  }
                }
              }
            }
          }
          catch (IOException localIOException)
          {
          }
        }
      }
    }
  }

  private static PendingIntent newViewOneIntent(Context paramContext, EsAccount paramEsAccount, Cursor paramCursor)
  {
    Intent localIntent1 = newViewNotificationIntent(paramContext, paramEsAccount, paramCursor);
    if ((localIntent1 != null) && (localIntent1.resolveActivity(paramContext.getPackageManager()) != null))
      localIntent1.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
    Intent localIntent2;
    for (PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent1, 0); ; localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent2, 0))
    {
      return localPendingIntent;
      localIntent2 = Intents.getNotificationsIntent(paramContext, paramEsAccount, paramCursor);
      localIntent2.addFlags(335544320);
      localIntent2.putExtra("com.google.plus.analytics.intent.extra.FROM_NOTIFICATION", true);
    }
  }

  public static boolean shouldNotify(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str = localResources.getString(R.string.notifications_preference_enabled_key);
    boolean bool = localResources.getBoolean(R.bool.notifications_preference_enabled_default_value);
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(str, bool);
  }

  public static boolean shouldVibrate(Context paramContext)
  {
    Resources localResources = paramContext.getResources();
    String str = localResources.getString(R.string.notifications_preference_vibrate_key);
    boolean bool = localResources.getBoolean(R.bool.notifications_preference_vibrate_default_value);
    return PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(str, bool);
  }

  public static void showCircleAddFailedNotification(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2)
  {
    Intent localIntent = Intents.getProfileActivityIntent(paramContext, paramEsAccount, paramString1, null);
    localIntent.setPackage(paramContext.getPackageName());
    localIntent.addFlags(335544320);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setSmallIcon(17301543);
    localBuilder.setAutoCancel(true);
    localBuilder.setContentTitle(paramContext.getString(R.string.cannot_add_to_circle_error_title));
    localBuilder.setContentText(paramContext.getString(R.string.cannot_add_to_circle_error_message, new Object[] { paramString2 }));
    localBuilder.setContentIntent(localPendingIntent);
    ((NotificationManager)paramContext.getSystemService("notification")).notify(paramContext.getPackageName() + ":notifications:add:" + paramString1, 3, localBuilder.getNotification());
  }

  public static void showFullSizeFirstTimeNotification(Context paramContext, EsAccount paramEsAccount)
  {
    Intent localIntent = Intents.getInstantUploadSettingsActivityIntent(paramContext, paramEsAccount);
    localIntent.setPackage(paramContext.getPackageName());
    localIntent.addFlags(335544320);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
    NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
    localBuilder.setSmallIcon(R.drawable.stat_notify_gplus);
    localBuilder.setAutoCancel(true);
    localBuilder.setContentTitle(paramContext.getString(R.string.full_size_first_time_notification_title));
    localBuilder.setTicker(paramContext.getString(R.string.full_size_first_time_notification_text));
    localBuilder.setContentText(paramContext.getString(R.string.full_size_first_time_notification_text));
    localBuilder.setContentIntent(localPendingIntent);
    ((NotificationManager)paramContext.getSystemService("notification")).notify(buildNotificationTag(paramContext, paramEsAccount), 5, localBuilder.getNotification());
  }

  public static void showQuotaNotification(Context paramContext, EsAccount paramEsAccount, int paramInt1, int paramInt2, boolean paramBoolean)
  {
    String str = InstantUpload.getSizeText(paramContext, Math.max(paramInt2 - paramInt1, 0));
    int i;
    if (paramBoolean)
    {
      i = R.string.full_size_no_quota_text;
      if (!paramBoolean)
        break label191;
    }
    label191: for (int j = R.drawable.stat_notify_quota_exceed; ; j = R.drawable.stat_notify_quota_warning)
    {
      Intent localIntent = Intents.getInstantUploadSettingsActivityIntent(paramContext, paramEsAccount);
      localIntent.setPackage(paramContext.getPackageName());
      localIntent.addFlags(335544320);
      PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
      NotificationCompat.Builder localBuilder = new NotificationCompat.Builder(paramContext);
      localBuilder.setSmallIcon(j);
      localBuilder.setAutoCancel(true);
      localBuilder.setContentTitle(paramContext.getString(R.string.instant_upload_notification_title));
      localBuilder.setTicker(paramContext.getString(i, new Object[] { str }));
      localBuilder.setContentText(paramContext.getString(i, new Object[] { str }));
      localBuilder.setContentIntent(localPendingIntent);
      ((NotificationManager)paramContext.getSystemService("notification")).notify(buildNotificationTag(paramContext, paramEsAccount), 4, localBuilder.getNotification());
      return;
      i = R.string.full_size_low_quota_text;
      break;
    }
  }

  public static void showUpgradeRequiredNotification(Context paramContext)
  {
    NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
    long l = System.currentTimeMillis();
    String str = paramContext.getString(R.string.signup_required_update_available);
    Notification localNotification = new Notification(R.drawable.ic_stat_gplus, str, l);
    Intent localIntent = new Intent("android.intent.action.VIEW");
    localIntent.addFlags(524288);
    localIntent.setData(Uri.parse("market://details?id=com.google.android.apps.plus"));
    localIntent.addFlags(335544320);
    PendingIntent localPendingIntent = PendingIntent.getActivity(paramContext, (int)System.currentTimeMillis(), localIntent, 0);
    localNotification.setLatestEventInfo(paramContext, paramContext.getString(R.string.app_name), str, localPendingIntent);
    localNotification.flags = (0x10 | localNotification.flags);
    localNotification.defaults = (0x4 | localNotification.defaults);
    localNotificationManager.notify(paramContext.getPackageName() + ":notifications:upgrade", 2, localNotification);
  }

  public static void update(Context paramContext, EsAccount paramEsAccount)
  {
    while (true)
    {
      try
      {
        NotificationManager localNotificationManager = (NotificationManager)paramContext.getSystemService("notification");
        String str = buildNotificationTag(paramContext, paramEsAccount);
        boolean bool = shouldNotify(paramContext);
        if (!bool)
          return;
        Notification localNotification = createNotification(paramContext, paramEsAccount);
        if (localNotification != null)
        {
          localNotification.flags = (0x10 | localNotification.flags);
          localNotification.flags = (0x1 | localNotification.flags);
          localNotification.flags = (0x8 | localNotification.flags);
          localNotification.ledARGB = -1;
          localNotification.ledOnMS = 500;
          localNotification.ledOffMS = 2000;
          if (shouldVibrate(paramContext))
            localNotification.defaults = (0x2 | localNotification.defaults);
          localNotificationManager.notify(str, 1, localNotification);
          continue;
        }
      }
      finally
      {
      }
      cancel(paramContext, paramEsAccount, 1);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.service.AndroidNotification
 * JD-Core Version:    0.6.2
 */