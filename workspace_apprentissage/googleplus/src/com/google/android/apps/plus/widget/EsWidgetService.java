package com.google.android.apps.plus.widget;

import android.app.IntentService;
import android.app.PendingIntent;
import android.appwidget.AppWidgetManager;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.Bitmap.Config;
import android.graphics.Canvas;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Build.VERSION;
import android.os.Bundle;
import android.support.v4.app.TaskStackBuilder;
import android.text.TextUtils;
import android.util.Log;
import android.widget.RemoteViews;
import com.google.android.apps.plus.R.color;
import com.google.android.apps.plus.R.id;
import com.google.android.apps.plus.R.integer;
import com.google.android.apps.plus.R.layout;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.AvatarRequest;
import com.google.android.apps.plus.content.DbEmbedMedia;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsAvatarData;
import com.google.android.apps.plus.content.EsMediaCache;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.MediaImageRequest;
import com.google.android.apps.plus.phone.Intents;
import com.google.android.apps.plus.phone.ScreenMetrics;
import com.google.android.apps.plus.phone.StreamAdapter.StreamQuery;
import com.google.android.apps.plus.service.EsSyncAdapterService.SyncState;
import com.google.android.apps.plus.service.ImageCache;
import com.google.android.apps.plus.service.ImageCache.ImageConsumer;
import com.google.android.apps.plus.util.BackgroundPatternUtils;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;

public class EsWidgetService extends IntentService
{
  private static final int[] TEXT_ONLY_VIEW_IDS = arrayOfInt;
  private static Bitmap sAuthorBitmap;
  private static int sAutoTextColor;
  private static int sContentColor;
  private static boolean sInitialized;
  public static int sWidgetImageFetchSize;

  static
  {
    int[] arrayOfInt = new int[3];
    arrayOfInt[0] = R.id.text_only_content_1;
    arrayOfInt[1] = R.id.text_only_content_2;
    arrayOfInt[2] = R.id.text_only_content_3;
  }

  public EsWidgetService()
  {
    super("EsWidgetService");
  }

  private static void fetchActivities(Context paramContext, EsAccount paramEsAccount, int paramInt, String paramString)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] loadActivities");
    int i;
    if (TextUtils.equals("v.whatshot", paramString))
    {
      i = 1;
      paramString = null;
    }
    while (true)
    {
      EsSyncAdapterService.SyncState localSyncState = new EsSyncAdapterService.SyncState();
      localSyncState.setFullSync(true);
      localSyncState.onSyncStart("Get activities for widget circleId: " + paramString + " view: " + i);
      localSyncState.onStart("Activities:SyncStream");
      String str = paramString;
      try
      {
        EsPostsData.doActivityStreamSync(paramContext, paramEsAccount, i, str, null, null, true, null, 20, null, localSyncState);
        return;
        boolean bool = TextUtils.equals("v.all.circles", paramString);
        i = 0;
        if (!bool)
          continue;
        i = 0;
        paramString = null;
      }
      catch (Exception localException)
      {
        while (true)
        {
          if (EsLog.isLoggable("EsWidget", 5))
            Log.w("EsWidget", "[" + paramInt + "] loadActivities failed: " + localException);
          localSyncState.onFinish();
          localSyncState.onSyncFinish();
        }
      }
      finally
      {
        localSyncState.onFinish();
        localSyncState.onSyncFinish();
      }
    }
  }

  private static String getAutoText(Context paramContext, long paramLong)
  {
    int i = EsPostsData.getDefaultText(paramLong);
    if (i != 0);
    for (String str = paramContext.getString(i); ; str = null)
      return str;
  }

  private Cursor loadCursor(EsAccount paramEsAccount, int paramInt, String paramString)
  {
    if (EsLog.isLoggable("EsWidget", 3))
      Log.d("EsWidget", "[" + paramInt + "] loadCursor");
    Uri localUri1;
    if ((TextUtils.isEmpty(paramString)) || (TextUtils.equals(paramString, "v.all.circles")))
      localUri1 = EsProvider.buildStreamUri(paramEsAccount, EsPostsData.buildActivitiesStreamKey(null, null, null, true, 0));
    while (true)
    {
      Uri localUri2 = localUri1.buildUpon().appendQueryParameter("limit", Integer.toString(10)).build();
      return getContentResolver().query(localUri2, StreamAdapter.StreamQuery.PROJECTION_STREAM, "content_flags&32933!=0 AND content_flags&16=0", null, "sort_index ASC");
      if (TextUtils.equals("v.whatshot", paramString))
        localUri1 = EsProvider.buildStreamUri(paramEsAccount, EsPostsData.buildActivitiesStreamKey(null, null, null, true, 1));
      else
        localUri1 = EsProvider.buildStreamUri(paramEsAccount, EsPostsData.buildActivitiesStreamKey(null, paramString, null, true, 0));
    }
  }

  private static MediaContent readMediaContent(Cursor paramCursor)
  {
    MediaContent localMediaContent = new MediaContent((byte)0);
    byte[] arrayOfByte = paramCursor.getBlob(22);
    DbEmbedMedia localDbEmbedMedia;
    String str1;
    if (arrayOfByte != null)
    {
      localDbEmbedMedia = DbEmbedMedia.deserialize(arrayOfByte);
      if ((localDbEmbedMedia != null) && (!TextUtils.isEmpty(localDbEmbedMedia.getImageUrl())))
      {
        str1 = localDbEmbedMedia.getImageUrl() + "&google_plus:widget";
        if (!localDbEmbedMedia.isVideo())
          break label129;
      }
    }
    label129: for (int i = 2; ; i = 3)
    {
      localMediaContent.imageRequest = new MediaImageRequest(str1, i, sWidgetImageFetchSize, sWidgetImageFetchSize, true);
      if (!TextUtils.isEmpty(localDbEmbedMedia.getTitle()))
      {
        String str2 = localDbEmbedMedia.getTitle();
        if (str2 != null)
          localMediaContent.linkTitle = str2.trim();
      }
      return localMediaContent;
    }
  }

  private static void showText(RemoteViews paramRemoteViews, int paramInt1, String paramString, int paramInt2)
  {
    paramRemoteViews.setViewVisibility(paramInt1, 0);
    paramRemoteViews.setTextViewText(paramInt1, paramString.trim());
    paramRemoteViews.setTextColor(paramInt1, paramInt2);
  }

  private static void showTextLayoutContent(Context paramContext, RemoteViews paramRemoteViews, String paramString1, String paramString2, String paramString3, long paramLong)
  {
    int i = TEXT_ONLY_VIEW_IDS.length;
    boolean bool = TextUtils.isEmpty(paramString1);
    int j = 0;
    if (!bool)
    {
      j = 0;
      if (i > 0)
      {
        int[] arrayOfInt5 = TEXT_ONLY_VIEW_IDS;
        j = 0 + 1;
        showText(paramRemoteViews, arrayOfInt5[0], paramString1, sContentColor);
      }
    }
    if ((!TextUtils.isEmpty(paramString2)) && (j < i))
    {
      int[] arrayOfInt4 = TEXT_ONLY_VIEW_IDS;
      int i1 = j + 1;
      showText(paramRemoteViews, arrayOfInt4[j], paramString2, sContentColor);
      j = i1;
    }
    int k;
    if ((!TextUtils.isEmpty(paramString3)) && (j < i))
    {
      int[] arrayOfInt3 = TEXT_ONLY_VIEW_IDS;
      k = j + 1;
      showText(paramRemoteViews, arrayOfInt3[j], paramString3, sContentColor);
    }
    while (true)
    {
      int n;
      if (k == 0)
      {
        String str = getAutoText(paramContext, paramLong);
        if (!TextUtils.isEmpty(str))
        {
          int[] arrayOfInt2 = TEXT_ONLY_VIEW_IDS;
          n = k + 1;
          showText(paramRemoteViews, arrayOfInt2[k], str, sAutoTextColor);
        }
      }
      int m;
      for (k = n; k < i; k = m)
      {
        int[] arrayOfInt1 = TEXT_ONLY_VIEW_IDS;
        m = k + 1;
        paramRemoteViews.setViewVisibility(arrayOfInt1[k], 8);
      }
      return;
      k = j;
    }
  }

  protected void onHandleIntent(Intent paramIntent)
  {
    if (!sInitialized)
    {
      Resources localResources = getResources();
      sAuthorBitmap = EsAvatarData.getSmallDefaultAvatar(this, true);
      sContentColor = localResources.getColor(R.color.stream_content_color);
      sAutoTextColor = localResources.getColor(R.color.card_auto_text);
      sWidgetImageFetchSize = Math.min(localResources.getInteger(R.integer.max_widget_image_size), ScreenMetrics.getInstance(this).shortDimension);
      sInitialized = true;
    }
    int i = paramIntent.getIntExtra("appWidgetId", 0);
    if (i == 0);
    String str1;
    boolean bool1;
    String str2;
    EsAccount localEsAccount;
    while (true)
    {
      return;
      str1 = EsWidgetUtils.loadCircleId(this, i);
      bool1 = paramIntent.getBooleanExtra("refresh", false);
      str2 = paramIntent.getStringExtra("activity_id");
      localEsAccount = EsAccountsData.getActiveAccount(this);
      if (localEsAccount == null)
      {
        EsWidgetProvider.showTapToConfigure(this, i);
      }
      else
      {
        if (!TextUtils.isEmpty(str1))
          break;
        EsWidgetProvider.showLoadingView(this, i);
      }
    }
    if (bool1)
      fetchActivities(this, localEsAccount, i, str1);
    Cursor localCursor = loadCursor(localEsAccount, i, str1);
    if (localCursor != null);
    while (true)
    {
      int k;
      RemoteViews localRemoteViews;
      long l;
      int m;
      int n;
      int i5;
      int i7;
      Bitmap localBitmap1;
      int i8;
      String str7;
      String str8;
      String str11;
      try
      {
        if ((localCursor.getCount() == 0) && (!bool1))
        {
          localCursor.close();
          fetchActivities(this, localEsAccount, i, str1);
          localCursor = loadCursor(localEsAccount, i, str1);
        }
        if ((localCursor == null) || (localCursor.getCount() == 0))
        {
          EsWidgetProvider.showNoPostsFound(this, localEsAccount, i);
          if (localCursor == null)
            break;
          localCursor.close();
          break;
        }
        if (!TextUtils.isEmpty(str2))
        {
          localCursor.moveToPosition(-1);
          if (localCursor.moveToNext())
          {
            if (!TextUtils.equals(localCursor.getString(1), str2))
              continue;
            if (!localCursor.moveToNext())
              localCursor.moveToFirst();
            if (localCursor.getCount() > 1)
            {
              int i9 = localCursor.getPosition();
              if (!localCursor.moveToNext())
                localCursor.moveToFirst();
              ImageCache localImageCache = ImageCache.getInstance(this);
              MediaContent localMediaContent2 = readMediaContent(localCursor);
              if (localMediaContent2.imageRequest != null)
                localImageCache.loadImage(new ImageCache.ImageConsumer()
                {
                  public final void setBitmap(Bitmap paramAnonymousBitmap, boolean paramAnonymousBoolean)
                  {
                  }
                }
                , localMediaContent2.imageRequest);
              AvatarRequest localAvatarRequest = new AvatarRequest(localCursor.getString(2), 2);
              localImageCache.loadImage(new ImageCache.ImageConsumer()
              {
                public final void setBitmap(Bitmap paramAnonymousBitmap, boolean paramAnonymousBoolean)
                {
                }
              }
              , localAvatarRequest);
              localCursor.moveToPosition(i9);
            }
            AppWidgetManager localAppWidgetManager = AppWidgetManager.getInstance(this);
            int j = Build.VERSION.SDK_INT;
            k = 0;
            if (j >= 17)
            {
              if (localAppWidgetManager.getAppWidgetOptions(i).getInt("appWidgetCategory", -1) != 2)
                break label1593;
              k = 1;
            }
            if (EsLog.isLoggable("EsWidget", 3))
              Log.d("EsWidget", "[" + i + "] createRemoteViews: " + localCursor.getPosition());
            localRemoteViews = new RemoteViews(getPackageName(), R.layout.widget_layout);
            localRemoteViews.setViewVisibility(R.id.widget_empty_layout, 8);
            localRemoteViews.setViewVisibility(R.id.image, 8);
            localRemoteViews.setViewVisibility(R.id.video_overlay, 8);
            localRemoteViews.setViewVisibility(R.id.link_overlay, 8);
            localRemoteViews.setViewVisibility(R.id.link_background, 8);
            localRemoteViews.setViewVisibility(R.id.link_title, 8);
            String str3 = localCursor.getString(1);
            if (str3 != null)
            {
              l = localCursor.getLong(15);
              MediaContent localMediaContent1 = readMediaContent(localCursor);
              if (TextUtils.isEmpty(localMediaContent1.linkTitle))
                break label1599;
              m = 1;
              if ((m != 0) || (localMediaContent1.imageRequest != null))
                break label1605;
              n = 1;
              if (n == 0)
                break label1370;
              i1 = R.id.text_only_user_image;
              if (n == 0)
                break label1378;
              i2 = R.id.text_only_user_name;
              if (n == 0)
                break label1386;
              i3 = R.id.text_only_stream_name;
              int i4 = R.id.widget_image_layout;
              if (n == 0)
                break label1611;
              i5 = 8;
              localRemoteViews.setViewVisibility(i4, i5);
              int i6 = R.id.widget_text_layout;
              if (n == 0)
                break label1617;
              i7 = 0;
              localRemoteViews.setViewVisibility(i6, i7);
              localRemoteViews.setImageViewBitmap(i1, sAuthorBitmap);
              String str4 = localCursor.getString(2);
              String str5 = EsAvatarData.loadAvatarUrl(this, localEsAccount, str4);
              if (str5 != null)
              {
                Bitmap localBitmap2 = EsMediaCache.obtainAvatar(this, localEsAccount, str4, str5, true);
                if (localBitmap2 != null)
                  localRemoteViews.setImageViewBitmap(i1, localBitmap2);
              }
              MediaImageRequest localMediaImageRequest = localMediaContent1.imageRequest;
              localBitmap1 = null;
              if (localMediaImageRequest == null)
                break label1582;
              localBitmap1 = EsMediaCache.obtainImage(this, localEsAccount, localMediaImageRequest);
              if ((localBitmap1 == null) && (m == 0))
              {
                localBitmap1 = Bitmap.createBitmap(sWidgetImageFetchSize, sWidgetImageFetchSize, Bitmap.Config.RGB_565);
                Canvas localCanvas = new Canvas(localBitmap1);
                BackgroundPatternUtils.getInstance(this);
                BitmapDrawable localBitmapDrawable = BackgroundPatternUtils.getBackgroundPattern(str3);
                localBitmapDrawable.setBounds(0, 0, sWidgetImageFetchSize, sWidgetImageFetchSize);
                localBitmapDrawable.draw(localCanvas);
              }
              if (localBitmap1 == null)
                break label1582;
              if ((localBitmap1.getWidth() > sWidgetImageFetchSize) || (localBitmap1.getHeight() > sWidgetImageFetchSize))
                localBitmap1 = ImageUtils.resizeAndCropBitmap(localBitmap1, sWidgetImageFetchSize, sWidgetImageFetchSize);
              localRemoteViews.setViewVisibility(R.id.image, 0);
              localRemoteViews.setImageViewBitmap(R.id.image, localBitmap1);
              if (localMediaImageRequest.getMediaType() != 2)
                break label1582;
              localRemoteViews.setViewVisibility(R.id.video_overlay, 0);
              break label1582;
              if (m != 0)
              {
                if (k == 0)
                {
                  localRemoteViews.setViewVisibility(R.id.link_title, 0);
                  localRemoteViews.setTextViewText(R.id.link_title, localMediaContent1.linkTitle);
                }
                if (i8 == 0)
                  break label1394;
                localRemoteViews.setViewVisibility(R.id.link_overlay, 0);
              }
              localRemoteViews.setTextViewText(i2, localCursor.getString(3));
              String str6 = getSharedPreferences("com.google.android.apps.plus.widget.EsWidgetUtils", 0).getString("circleName_" + i, null);
              if (TextUtils.isEmpty(str6))
                str6 = null;
              if (TextUtils.isEmpty(str6))
                str6 = getString(R.string.widget_default_circle_name);
              localRemoteViews.setTextViewText(i3, getString(R.string.from_circle, new Object[] { str6 }));
              str7 = localCursor.getString(16);
              str8 = localCursor.getString(17);
              String str9 = localCursor.getString(18);
              String str10 = localCursor.getString(19);
              boolean bool2 = TextUtils.isEmpty(str9);
              str11 = null;
              if (!bool2)
              {
                boolean bool3 = TextUtils.isEmpty(str10);
                str11 = null;
                if (!bool3)
                  str11 = getString(R.string.stream_original_author, new Object[] { str10 });
              }
              if (n == 0)
                break label1406;
              showTextLayoutContent(this, localRemoteViews, str7, str11, str8, l);
              EsWidgetProvider.configureHeaderButtons(this, localEsAccount, i, localRemoteViews, str3, true);
              if ((localEsAccount != null) && (str3 != null))
              {
                Intent localIntent = Intents.getPostCommentsActivityIntent(this, localEsAccount, str3);
                localIntent.setAction("com.google.android.apps.plus.widget.ACTIVITY_ACTION");
                localIntent.addFlags(335544320);
                localIntent.setData(Uri.parse(localIntent.toUri(1)));
                TaskStackBuilder localTaskStackBuilder = TaskStackBuilder.create(this);
                localTaskStackBuilder.addNextIntent(Intents.getHostNavigationActivityIntent(this, localEsAccount));
                localTaskStackBuilder.addNextIntent(localIntent);
                PendingIntent localPendingIntent = localTaskStackBuilder.getPendingIntent(0, 134217728, null);
                localRemoteViews.setOnClickPendingIntent(R.id.widget_main, localPendingIntent);
              }
            }
            localAppWidgetManager.updateAppWidget(i, localRemoteViews);
            continue;
          }
        }
      }
      finally
      {
        if (localCursor != null)
          localCursor.close();
      }
      localCursor.moveToFirst();
      continue;
      label1370: int i1 = R.id.user_image;
      continue;
      label1378: int i2 = R.id.user_name;
      continue;
      label1386: int i3 = R.id.stream_name;
      continue;
      label1394: localRemoteViews.setViewVisibility(R.id.link_background, 0);
      continue;
      label1406: localRemoteViews.setViewVisibility(R.id.content_line_1, 8);
      localRemoteViews.setViewVisibility(R.id.content_line_2, 8);
      localRemoteViews.setViewVisibility(R.id.content_multiline, 8);
      if ((!TextUtils.isEmpty(str7)) && (!TextUtils.isEmpty(str11)))
      {
        showText(localRemoteViews, R.id.content_line_1, str7, sContentColor);
        showText(localRemoteViews, R.id.content_line_2, str11, sContentColor);
      }
      else if (!TextUtils.isEmpty(str11))
      {
        showText(localRemoteViews, R.id.content_line_1, str11, sContentColor);
        if (!TextUtils.isEmpty(str8))
          showText(localRemoteViews, R.id.content_line_2, str8, sContentColor);
      }
      else if (!TextUtils.isEmpty(str8))
      {
        showText(localRemoteViews, R.id.content_multiline, str8, sContentColor);
      }
      else
      {
        String str12 = getAutoText(this, l);
        if (!TextUtils.isEmpty(str12))
        {
          showText(localRemoteViews, R.id.content_multiline, str12, sAutoTextColor);
          continue;
          label1582: if (localBitmap1 != null)
          {
            i8 = 1;
            continue;
            label1593: k = 0;
            continue;
            label1599: m = 0;
            continue;
            label1605: n = 0;
            continue;
            label1611: i5 = 0;
            continue;
            label1617: i7 = 8;
          }
          else
          {
            i8 = 0;
          }
        }
      }
    }
  }

  public void onStart(Intent paramIntent, int paramInt)
  {
    super.onStart(paramIntent, paramInt);
    int i = paramIntent.getIntExtra("appWidgetId", 0);
    String str;
    if (i != 0)
    {
      str = EsWidgetUtils.loadCircleId(this, i);
      if (EsAccountsData.getActiveAccount(this) != null)
        break label39;
      EsWidgetProvider.showTapToConfigure(this, i);
    }
    while (true)
    {
      return;
      label39: if (TextUtils.isEmpty(str))
        EsWidgetProvider.showLoadingView(this, i);
      else
        EsWidgetProvider.showProgressIndicator(this, i, paramIntent.getBooleanExtra("refresh", false));
    }
  }

  private static final class MediaContent
  {
    public MediaImageRequest imageRequest;
    public String linkTitle;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.widget.EsWidgetService
 * JD-Core Version:    0.6.2
 */