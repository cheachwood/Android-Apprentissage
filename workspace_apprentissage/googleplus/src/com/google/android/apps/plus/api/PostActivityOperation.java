package com.google.android.apps.plus.api;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.Uri.Builder;
import android.os.Handler;
import android.os.Looper;
import android.util.Base64;
import android.util.Log;
import com.android.gallery3d.common.Fingerprint;
import com.google.android.apps.plus.analytics.EsAnalytics;
import com.google.android.apps.plus.analytics.OzActions;
import com.google.android.apps.plus.analytics.OzViews;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.DbEmbedEmotishare;
import com.google.android.apps.plus.content.DbLocation;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAccountsData;
import com.google.android.apps.plus.content.EsPhotosData;
import com.google.android.apps.plus.content.EsProvider;
import com.google.android.apps.plus.content.LocalImageRequest;
import com.google.android.apps.plus.network.ApiaryActivity;
import com.google.android.apps.plus.network.ApiaryApiInfo;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlatformHttpRequestConfiguration;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.android.apps.plus.util.EsLog;
import com.google.android.apps.plus.util.ImageUtils;
import com.google.android.apps.plus.util.Property;
import com.google.android.picasastore.PicasaStoreFacade;
import com.google.api.services.plusi.model.PhotoServiceMediaReference;
import com.google.api.services.plusi.model.PhotoServiceMediaReferencePhoto;
import com.google.api.services.plusi.model.PhotoServiceShareActionData;
import com.google.api.services.plusi.model.PostActivityRequest;
import com.google.api.services.plusi.model.PostActivityRequestJson;
import com.google.api.services.plusi.model.PostActivityResponse;
import com.google.api.services.plusi.model.PostActivityResponseJson;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

public final class PostActivityOperation extends PlusiOperation<PostActivityRequest, PostActivityResponse>
{
  private final ApiaryActivity mActivity;
  private final ApiaryApiInfo mApiInfo;
  private final List<MediaRef> mAttachments;
  private final AudienceData mAudience;
  private final BirthdayData mBirthdayData;
  private final String mContent;
  private final String mDeepLinkId;
  private final DbEmbedEmotishare mEmotiShare;
  private final String mExternalId;
  private final DbLocation mLocation;
  private final PackageManager mPackageManager;
  private List<MediaRef> mPostedAttachments;
  private final boolean mSaveAcl;

  public PostActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, ApiaryActivity paramApiaryActivity, String paramString1, List<MediaRef> paramList, String paramString2, DbLocation paramDbLocation, AudienceData paramAudienceData, ApiaryApiInfo paramApiaryApiInfo, String paramString3, boolean paramBoolean, BirthdayData paramBirthdayData, DbEmbedEmotishare paramDbEmbedEmotishare)
  {
    super(paramContext, paramEsAccount, "postactivity", PostActivityRequestJson.getInstance(), PostActivityResponseJson.getInstance(), paramIntent, paramOperationListener, new PlatformHttpRequestConfiguration(paramContext, paramEsAccount, "oauth2:https://www.googleapis.com/auth/plus.me https://www.googleapis.com/auth/plus.stream.read https://www.googleapis.com/auth/plus.stream.write https://www.googleapis.com/auth/plus.circles.write https://www.googleapis.com/auth/plus.circles.read https://www.googleapis.com/auth/plus.photos.readwrite https://www.googleapis.com/auth/plus.native", Property.PLUS_BACKEND_URL.get(), paramApiaryApiInfo));
    this.mActivity = paramApiaryActivity;
    this.mContent = paramString1;
    this.mAttachments = paramList;
    this.mExternalId = paramString2;
    this.mLocation = paramDbLocation;
    this.mAudience = paramAudienceData;
    this.mApiInfo = paramApiaryApiInfo;
    this.mPackageManager = paramContext.getPackageManager();
    this.mDeepLinkId = paramString3;
    this.mSaveAcl = paramBoolean;
    this.mBirthdayData = paramBirthdayData;
    this.mEmotiShare = paramDbEmbedEmotishare;
  }

  private static Fingerprint getFingerprint(Context paramContext, long paramLong)
  {
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
    Uri localUri = EsProvider.appendAccountParameter(ContentUris.withAppendedId(EsProvider.PHOTO_BY_PHOTO_ID_URI, paramLong), localEsAccount);
    String[] arrayOfString = { "fingerprint" };
    Cursor localCursor = paramContext.getContentResolver().query(localUri, arrayOfString, null, null, null);
    Fingerprint localFingerprint = null;
    if (localCursor != null);
    try
    {
      if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
      {
        localFingerprint = new Fingerprint(localCursor.getBlob(0));
        return localFingerprint;
      }
      localCursor.close();
      localFingerprint = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  private static Fingerprint getFingerprint(Context paramContext, Uri paramUri)
  {
    Uri localUri = PicasaStoreFacade.get(paramContext).getFingerprintUri(true, false);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = paramUri.toString();
    Cursor localCursor = paramContext.getContentResolver().query(localUri, arrayOfString, null, null, null);
    Fingerprint localFingerprint = null;
    if (localCursor != null);
    try
    {
      if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
      {
        localFingerprint = new Fingerprint(localCursor.getBlob(0));
        return localFingerprint;
      }
      localCursor.close();
      localFingerprint = null;
    }
    finally
    {
      localCursor.close();
    }
  }

  private static long getPhotoIdFromStream(Context paramContext, String paramString)
  {
    EsAccount localEsAccount = EsAccountsData.getActiveAccount(paramContext);
    Uri localUri = EsProvider.appendAccountParameter(Uri.withAppendedPath(EsProvider.PHOTO_BY_STREAM_ID_AND_OWNER_ID_URI.buildUpon().appendPath(paramString).build(), localEsAccount.getGaiaId()), localEsAccount);
    String[] arrayOfString = { "photo_id" };
    Cursor localCursor = paramContext.getContentResolver().query(localUri, arrayOfString, null, null, null);
    if (localCursor != null);
    try
    {
      if ((localCursor.moveToFirst()) && (!localCursor.isNull(0)))
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
        return l1;
      }
      localCursor.close();
      long l1 = 0L;
    }
    finally
    {
      localCursor.close();
    }
  }

  private PhotoServiceShareActionData getPhotosShareData(List<MediaRef> paramList)
  {
    PhotoServiceShareActionData localPhotoServiceShareActionData;
    if (paramList.isEmpty())
      localPhotoServiceShareActionData = null;
    while (true)
    {
      return localPhotoServiceShareActionData;
      ArrayList localArrayList = new ArrayList();
      HashSet localHashSet1 = new HashSet(this.mAttachments.size());
      HashSet localHashSet2 = new HashSet(this.mAttachments.size());
      ContentResolver localContentResolver = this.mContext.getContentResolver();
      int i = 0;
      Iterator localIterator = paramList.iterator();
      if (localIterator.hasNext())
      {
        Object localObject1 = (MediaRef)localIterator.next();
        int j;
        label122: Context localContext1;
        List localList;
        PhotoServiceMediaReference localPhotoServiceMediaReference;
        String str1;
        label159: Fingerprint localFingerprint2;
        if ((i < 4) && (this.mPostedAttachments.size() < 4))
        {
          j = 1;
          localContext1 = this.mContext;
          localList = this.mPostedAttachments;
          localPhotoServiceMediaReference = new PhotoServiceMediaReference();
          if (((MediaRef)localObject1).getType() != MediaRef.MediaType.VIDEO)
            break label257;
          str1 = "2";
          localPhotoServiceMediaReference.mediaType = str1;
          if (!((MediaRef)localObject1).hasLocalUri())
            break label672;
          Uri localUri = ((MediaRef)localObject1).getLocalUri();
          localFingerprint2 = getFingerprint(localContext1, localUri);
          if (localFingerprint2 != null)
            break label275;
          Log.w("HttpTransaction", "Could not determine fingerprint for media: " + localUri);
          localPhotoServiceMediaReference = null;
        }
        label223: Fingerprint localFingerprint1;
        while (true)
          if (localPhotoServiceMediaReference != null)
          {
            localArrayList.add(localPhotoServiceMediaReference);
            if (localPhotoServiceMediaReference.imageData == null)
              break;
            i++;
            break;
            j = 0;
            break label122;
            label257: ((MediaRef)localObject1).getType();
            str1 = "1";
            break label159;
            label275: long l1 = ((MediaRef)localObject1).getPhotoId();
            Object localObject2;
            if (l1 != 0L)
            {
              Fingerprint localFingerprint3 = getFingerprint(this.mContext, l1);
              if (!localFingerprint2.equals(localFingerprint3))
              {
                long l3 = getPhotoIdFromStream(this.mContext, localFingerprint2.toStreamId());
                Log.w("HttpTransaction", "Fingerprint mismatch; old: " + localFingerprint3.toStreamId() + ", new: " + localFingerprint2.toStreamId());
                final Context localContext2 = this.mContext;
                final EsAccount localEsAccount = this.mAccount;
                new Handler(Looper.getMainLooper()).post(new Runnable()
                {
                  public final void run()
                  {
                    OzViews localOzViews = OzViews.getViewForLogging(localContext2);
                    EsAnalytics.recordActionEvent(localContext2, localEsAccount, OzActions.PHOTOS_SURPRISE_CAID, localOzViews);
                  }
                });
                localObject2 = new MediaRef(((MediaRef)localObject1).getOwnerGaiaId(), l3, ((MediaRef)localObject1).getUrl(), ((MediaRef)localObject1).getLocalUri(), ((MediaRef)localObject1).getType());
                label435: localObject1 = localObject2;
                localFingerprint1 = localFingerprint2;
              }
            }
            while (true)
            {
              label672: String str2;
              String str3;
              Bitmap localBitmap;
              if ((((MediaRef)localObject1).hasPhotoId()) && (localHashSet1.contains(Long.valueOf(((MediaRef)localObject1).getPhotoId()))))
              {
                if (EsLog.isLoggable("HttpTransaction", 3))
                  Log.d("HttpTransaction", "Duplicate server reference found; " + localObject1);
                localPhotoServiceMediaReference = null;
                break;
                localFingerprint1 = localFingerprint2;
                continue;
                if (EsLog.isLoggable("HttpTransaction", 3))
                  Log.d("HttpTransaction", "Looking for remote photo w/ CAID: " + localFingerprint2.toStreamId());
                long l2 = getPhotoIdFromStream(this.mContext, localFingerprint2.toStreamId());
                if (l2 != 0L)
                {
                  if (EsLog.isLoggable("HttpTransaction", 3))
                    Log.d("HttpTransaction", "Found remote photo; ID: " + l2 + " matches CAID: " + localFingerprint2.toStreamId());
                  localObject1 = new MediaRef(EsAccountsData.getActiveAccount(localContext1).getGaiaId(), l2, ((MediaRef)localObject1).getUrl(), ((MediaRef)localObject1).getLocalUri(), ((MediaRef)localObject1).getType());
                  localFingerprint1 = localFingerprint2;
                }
                else
                {
                  localObject2 = localObject1;
                  break label435;
                  if (((MediaRef)localObject1).hasPhotoId())
                  {
                    localFingerprint1 = null;
                  }
                  else
                  {
                    Log.w("HttpTransaction", "No photo ID or local Uri for attachment: " + localObject1);
                    localPhotoServiceMediaReference = null;
                    break;
                  }
                }
              }
            }
            if (localFingerprint1 != null);
            for (str2 = localFingerprint1.toStreamId(); ; str2 = null)
            {
              if ((str2 == null) || (!localHashSet2.contains(str2)))
                break label793;
              if (EsLog.isLoggable("HttpTransaction", 3))
                Log.d("HttpTransaction", "Duplicate CAID found; " + localObject1);
              localPhotoServiceMediaReference = null;
              break;
            }
            label793: if (((MediaRef)localObject1).hasPhotoId())
              break label969;
            if (str2 != null)
            {
              localPhotoServiceMediaReference.clientAssignedUniqueId = str2;
              localHashSet2.add(str2);
            }
            str3 = ImageUtils.getMimeType(localContentResolver, ((MediaRef)localObject1).getLocalUri());
            if ((j != 0) && ("image/jpeg".equals(str3)))
            {
              localBitmap = EsPhotosData.loadLocalBitmap(localContext1, new LocalImageRequest((MediaRef)localObject1, 320, 320));
              if (localBitmap == null)
                Log.e("HttpTransaction", "Bitmap decoding failed for " + ((MediaRef)localObject1).getLocalUri());
            }
          }
        while (true)
        {
          localList.add(localObject1);
          break label223;
          localPhotoServiceMediaReference.imageData = Base64.encodeToString(ImageUtils.compressBitmap(localBitmap, 85, true), 0);
          localPhotoServiceMediaReference.imageStatus = "1";
          continue;
          if (localFingerprint1 == null)
          {
            localPhotoServiceMediaReference = null;
            break label223;
            break;
          }
          localPhotoServiceMediaReference.imageStatus = "2";
          continue;
          label969: PhotoServiceMediaReferencePhoto localPhotoServiceMediaReferencePhoto = new PhotoServiceMediaReferencePhoto();
          localPhotoServiceMediaReferencePhoto.obfuscatedOwnerId = ((MediaRef)localObject1).getOwnerGaiaId();
          localPhotoServiceMediaReferencePhoto.photoId = Long.toString(((MediaRef)localObject1).getPhotoId());
          localPhotoServiceMediaReference.sourcePhoto = localPhotoServiceMediaReferencePhoto;
          localHashSet1.add(Long.valueOf(((MediaRef)localObject1).getPhotoId()));
        }
      }
      if (localArrayList.isEmpty())
      {
        localPhotoServiceShareActionData = null;
      }
      else
      {
        localPhotoServiceShareActionData = new PhotoServiceShareActionData();
        localPhotoServiceShareActionData.mediaRef = localArrayList;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PostActivityOperation
 * JD-Core Version:    0.6.2
 */