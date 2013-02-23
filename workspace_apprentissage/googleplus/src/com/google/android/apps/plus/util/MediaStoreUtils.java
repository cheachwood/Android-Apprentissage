package com.google.android.apps.plus.util;

import android.content.ContentResolver;
import android.content.ContentUris;
import android.content.Context;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.provider.MediaStore.Images.Media;
import android.provider.MediaStore.Images.Thumbnails;
import android.provider.MediaStore.Video.Media;
import android.provider.MediaStore.Video.Thumbnails;
import android.text.TextUtils;
import android.util.Log;
import com.google.api.services.plusi.model.DataVideo;
import com.google.api.services.plusi.model.DataVideoJson;
import com.google.api.services.plusi.model.DataVideoStream;
import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class MediaStoreUtils
{
  public static final String[] MEDIA_ID_PROJECTION = { "_id" };
  private static final Pattern PAT_RESOLUTION = Pattern.compile("(\\d+)[xX](\\d+)");
  public static final Uri PHONE_STORAGE_IMAGES_URI = MediaStore.Images.Media.getContentUri("phoneStorage");
  public static final Uri PHONE_STORAGE_VIDEO_URI = MediaStore.Video.Media.getContentUri("phoneStorage");

  public static boolean deleteLocalFileAndMediaStore(ContentResolver paramContentResolver, Uri paramUri)
  {
    int i = 1;
    String str = getFilePath(paramContentResolver, paramUri);
    if (paramContentResolver.delete(paramUri, null, null) == i);
    while (true)
    {
      if ((i != 0) && (str != null))
      {
        File localFile = new File(str);
        if (localFile.exists())
          bool = localFile.delete();
      }
      return bool;
      boolean bool = false;
    }
  }

  public static String getFilePath(ContentResolver paramContentResolver, Uri paramUri)
  {
    Cursor localCursor = paramContentResolver.query(paramUri, new String[] { "_data" }, null, null, null);
    Object localObject2;
    if (localCursor == null)
    {
      boolean bool = EsLog.isLoggable("MediaStoreUtils", 5);
      localObject2 = null;
      if (bool)
        Log.w("MediaStoreUtils", "getFilePath: query returned null cursor for uri=" + paramUri);
    }
    while (true)
    {
      return localObject2;
      try
      {
        if (!localCursor.moveToFirst())
        {
          if (EsLog.isLoggable("MediaStoreUtils", 5))
            Log.w("MediaStoreUtils", "getFilePath: query returned empty cursor for uri=" + paramUri);
          localCursor.close();
          localObject2 = null;
          continue;
        }
        String str = localCursor.getString(0);
        if (TextUtils.isEmpty(str))
        {
          if (EsLog.isLoggable("MediaStoreUtils", 5))
            Log.w("MediaStoreUtils", "getFilePath: MediaColumns.DATA was empty for uri=" + paramUri);
          localCursor.close();
          localObject2 = null;
          continue;
        }
        localCursor.close();
        localObject2 = str;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static long getMediaId(ContentResolver paramContentResolver, Uri paramUri)
  {
    Cursor localCursor = null;
    try
    {
      localCursor = paramContentResolver.query(paramUri, MEDIA_ID_PROJECTION, null, null, null);
      if (localCursor.moveToNext())
      {
        long l2 = localCursor.getLong(0);
        l1 = l2;
        return l1;
      }
      if (localCursor != null)
        localCursor.close();
      long l1 = -1L;
    }
    finally
    {
      if (localCursor != null)
        localCursor.close();
    }
  }

  public static Bitmap getThumbnail(Context paramContext, Uri paramUri, int paramInt)
  {
    int i = ImageUtils.getMaxThumbnailDimension(paramContext, 1);
    return getThumbnailHelper(paramContext, paramUri, i, i, 1);
  }

  public static Bitmap getThumbnail(Context paramContext, Uri paramUri, int paramInt1, int paramInt2)
  {
    int i = 3;
    int j = ImageUtils.getMaxThumbnailDimension(paramContext, i);
    if ((paramInt1 > j) || (paramInt2 > j))
      i = 1;
    return getThumbnailHelper(paramContext, paramUri, paramInt1, paramInt2, i);
  }

  private static Bitmap getThumbnailHelper(Context paramContext, Uri paramUri, int paramInt1, int paramInt2, int paramInt3)
  {
    Object localObject = null;
    if (paramUri == null);
    while (true)
    {
      return localObject;
      boolean bool1 = isExternalMediaStoreUri(paramUri);
      localObject = null;
      if (bool1)
      {
        ContentResolver localContentResolver = paramContext.getContentResolver();
        long l = ContentUris.parseId(paramUri);
        String str = ImageUtils.getMimeType(localContentResolver, paramUri);
        if (ImageUtils.isImageMimeType(str));
        for (localObject = MediaStore.Images.Thumbnails.getThumbnail(localContentResolver, l, paramInt3, null); ; localObject = MediaStore.Video.Thumbnails.getThumbnail(localContentResolver, l, paramInt3, null))
        {
          if (localObject == null)
            break label146;
          localObject = ImageUtils.rotateBitmap(localContentResolver, paramUri, (Bitmap)localObject);
          if ((((Bitmap)localObject).getWidth() == paramInt1) && (((Bitmap)localObject).getHeight() == paramInt2))
            break;
          Bitmap localBitmap = ImageUtils.resizeAndCropBitmap((Bitmap)localObject, paramInt1, paramInt2);
          if (localBitmap == localObject)
            break;
          ((Bitmap)localObject).recycle();
          localObject = localBitmap;
          break;
          if (!ImageUtils.isVideoMimeType(str))
            break label148;
        }
        label146: continue;
        label148: boolean bool2 = EsLog.isLoggable("MediaStoreUtils", 5);
        localObject = null;
        if (bool2)
        {
          Log.w("MediaStoreUtils", "getThumbnail: unrecognized mimeType=" + str + ", uri=" + paramUri);
          localObject = null;
        }
      }
    }
  }

  public static boolean isExternalMediaStoreUri(Uri paramUri)
  {
    boolean bool1 = isMediaStoreUri(paramUri);
    boolean bool2 = false;
    if (bool1)
    {
      String str1 = paramUri.getPath();
      String str2 = MediaStore.Images.Media.EXTERNAL_CONTENT_URI.getPath();
      String str3 = MediaStore.Video.Media.EXTERNAL_CONTENT_URI.getPath();
      if (!str1.startsWith(str2))
      {
        boolean bool3 = str1.startsWith(str3);
        bool2 = false;
        if (!bool3);
      }
      else
      {
        bool2 = true;
      }
    }
    return bool2;
  }

  public static boolean isMediaStoreUri(Uri paramUri)
  {
    if ((paramUri != null) && ("content".equals(paramUri.getScheme())) && ("media".equals(paramUri.getAuthority())));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  public static DataVideo toVideoData(Context paramContext, Uri paramUri)
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    DataVideo localDataVideo;
    if (!ImageUtils.isVideoMimeType(ImageUtils.getMimeType(localContentResolver, paramUri)))
      localDataVideo = null;
    while (true)
    {
      return localDataVideo;
      DataVideoStream localDataVideoStream = new DataVideoStream();
      localDataVideoStream.url = paramUri.toString();
      localDataVideoStream.formatId = Integer.valueOf(0);
      long l = 0L;
      Cursor localCursor = localContentResolver.query(paramUri, VideoQuery.PROJECTION, null, null, null);
      int i = 0;
      int j = 0;
      if (localCursor != null);
      try
      {
        boolean bool1 = localCursor.moveToFirst();
        i = 0;
        j = 0;
        if (bool1)
        {
          l = localCursor.getLong(1);
          String str = localCursor.getString(2);
          i = 0;
          j = 0;
          if (str != null)
          {
            Matcher localMatcher = PAT_RESOLUTION.matcher(str);
            boolean bool2 = localMatcher.find();
            i = 0;
            j = 0;
            if (bool2)
            {
              j = Integer.parseInt(localMatcher.group(1));
              int k = Integer.parseInt(localMatcher.group(2));
              i = k;
            }
          }
        }
        localCursor.close();
        localDataVideoStream.width = Integer.valueOf(j);
        localDataVideoStream.height = Integer.valueOf(i);
        ArrayList localArrayList = new ArrayList(1);
        localArrayList.add(localDataVideoStream);
        localDataVideo = new DataVideo();
        localDataVideo.status = "FINAL";
        localDataVideo.durationMillis = Long.valueOf(l);
        localDataVideo.stream = localArrayList;
      }
      finally
      {
        localCursor.close();
      }
    }
  }

  public static byte[] toVideoDataBytes(Context paramContext, Uri paramUri)
  {
    DataVideo localDataVideo = toVideoData(paramContext, paramUri);
    if (localDataVideo == null);
    for (byte[] arrayOfByte = null; ; arrayOfByte = DataVideoJson.getInstance().toByteArray(localDataVideo))
      return arrayOfByte;
  }

  private static abstract interface VideoQuery
  {
    public static final String[] PROJECTION = { "_id", "duration", "resolution" };
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.MediaStoreUtils
 * JD-Core Version:    0.6.2
 */