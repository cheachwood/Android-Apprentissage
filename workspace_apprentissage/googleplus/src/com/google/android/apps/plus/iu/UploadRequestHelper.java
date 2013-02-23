package com.google.android.apps.plus.iu;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.res.AssetFileDescriptor;
import android.net.Uri;
import android.net.Uri.Builder;
import android.text.TextUtils;
import android.util.Log;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLConnection;
import java.util.Date;

final class UploadRequestHelper
{
  private static final Uri PICASA_BASE_UPLOAD_URL = Uri.parse("https://picasaweb.google.com/data/upload/resumable/media/create-session/feed/api/user/default/albumid/");
  private static final String[] PROJECTION_DATA = { "_data" };
  private static final String[] PROJECTION_DATE_TAKEN = { "datetaken" };
  private static final String[] PROJECTION_SIZE = { "_size" };

  static ContentValues createUploadTask(String paramString1, Uri paramUri, long paramLong, String paramString2, String paramString3)
  {
    String str = paramUri.toString();
    ContentValues localContentValues = new ContentValues();
    localContentValues.put("account", paramString1);
    localContentValues.put("content_uri", str);
    localContentValues.put("media_record_id", Long.valueOf(paramLong));
    localContentValues.put("priority", Integer.valueOf(2));
    if (paramString2 == null)
      paramString2 = "camera-sync";
    localContentValues.put("album_id", paramString2);
    localContentValues.put("event_id", paramString3);
    return localContentValues;
  }

  static boolean fillRequest(Context paramContext, UploadTaskEntry paramUploadTaskEntry, boolean paramBoolean)
    throws IOException
  {
    ContentResolver localContentResolver = paramContext.getContentResolver();
    Uri localUri1 = paramUploadTaskEntry.getContentUri();
    if (EsLog.isLoggable("iu.UploadsManager", 3))
      Log.d("iu.UploadsManager", "fill request for " + localUri1);
    String str1 = paramUploadTaskEntry.getEventId();
    String str2 = paramUploadTaskEntry.getAlbumId();
    if (str2 == null)
    {
      str2 = "camera-sync";
      paramUploadTaskEntry.setAlbumId(str2);
    }
    paramUploadTaskEntry.setAuthTokenType("lh2");
    if (!paramUploadTaskEntry.hasPriority())
      paramUploadTaskEntry.setPriority(1);
    Fingerprint localFingerprint;
    String str3;
    if (!paramUploadTaskEntry.hasFingerprint())
    {
      long[] arrayOfLong = new long[1];
      localFingerprint = Fingerprint.fromInputStream(localContentResolver.openInputStream(localUri1), arrayOfLong);
      paramUploadTaskEntry.setBytesTotal(arrayOfLong[0]);
      if (arrayOfLong[0] > 0L)
      {
        paramUploadTaskEntry.setFingerprint(localFingerprint);
        str3 = paramUploadTaskEntry.getMimeType();
        if (str3 != null)
          break label425;
        str3 = setContentType(localContentResolver, paramUploadTaskEntry);
        if ((str3 == null) || ((!str3.startsWith("image/")) && (!str3.startsWith("video/"))))
          break label419;
      }
    }
    boolean bool;
    label419: for (int j = 1; ; j = 0)
    {
      if (j != 0)
        break label425;
      throw new IllegalArgumentException("invalid MIME type " + str3 + ":" + Utils.maskDebugInfo(localUri1));
      if (EsLog.isLoggable("iu.UploadsManager", 5))
        Log.w("iu.UploadsManager", "Could not generate fingerprint; media length is '0'");
      bool = false;
      return bool;
      if (paramUploadTaskEntry.getBytesTotal() <= 0L)
      {
        Uri localUri2 = paramUploadTaskEntry.getContentUri();
        long l3 = getOptionalLong(localContentResolver, localUri2, PROJECTION_SIZE, 0L);
        if ((l3 > 0L) && (EsLog.isLoggable("iu.UploadsManager", 3)))
          Log.d("iu.UploadsManager", "   media size from resolver: " + l3);
        if (l3 == 0L)
        {
          l3 = getFileLengthFromRawFdOrContent(localContentResolver, localUri2);
          if ((l3 > 0L) && (EsLog.isLoggable("iu.UploadsManager", 3)))
            Log.d("iu.UploadsManager", "   media size from content: " + l3);
        }
        if (l3 == 0L)
        {
          StringBuilder localStringBuilder3 = new StringBuilder("no content to upload: ");
          throw new RuntimeException(localUri2);
        }
        paramUploadTaskEntry.setBytesTotal(l3);
      }
      localFingerprint = paramUploadTaskEntry.getFingerprint();
      break;
    }
    label425: long l1 = getOptionalLong(localContentResolver, localUri1, PROJECTION_DATE_TAKEN, 0L);
    if (l1 > 0L);
    for (Date localDate = new Date(l1); ; localDate = null)
    {
      String str4 = getOptionalString(localContentResolver, localUri1, PROJECTION_DATA);
      if (str4 == null)
        str4 = localUri1.toString();
      String str5 = localFingerprint.toStreamId();
      String str6 = Utils.getUserAgent(paramContext);
      long l2 = paramUploadTaskEntry.getBytesTotal();
      int i = str4.lastIndexOf("/");
      if (i > 0)
        str4 = str4.substring(i + 1);
      if (localDate == null)
        localDate = new Date();
      StringBuilder localStringBuilder1 = new StringBuilder("Authorization: GoogleLogin auth=%=_auth_token_=%\r\nUser-Agent: ").append(str6).append("\r\nGData-Version: 3.0").append("\r\nSlug: ").append(str4).append("\r\nX-Upload-Content-Type: ").append(str3).append("\r\nX-Upload-Content-Length: ").append(Long.toString(l2)).append("\r\nContent-Type: application/atom+xml; charset=UTF-8").append("\r\n");
      StringBuilder localStringBuilder2 = new StringBuilder("\r\n<entry xmlns='http://www.w3.org/2005/Atom' xmlns:gphoto='http://schemas.google.com/photos/2007'><category scheme='http://schemas.google.com/g/2005#kind' term='http://schemas.google.com/photos/2007#photo'/>");
      localStringBuilder2.append("<title>");
      localStringBuilder2.append(Utils.escapeXml(str4));
      localStringBuilder2.append("</title>");
      if (!TextUtils.isEmpty(null))
      {
        localStringBuilder2.append("<summary>");
        localStringBuilder2.append(Utils.escapeXml(null));
        localStringBuilder2.append("</summary>");
      }
      localStringBuilder2.append("<gphoto:timestamp>");
      localStringBuilder2.append(localDate.getTime());
      localStringBuilder2.append("</gphoto:timestamp>");
      if (!TextUtils.isEmpty(str5))
      {
        localStringBuilder2.append("<gphoto:streamId>");
        localStringBuilder2.append(str5);
        localStringBuilder2.append("</gphoto:streamId>");
      }
      localStringBuilder2.append("<gphoto:streamId>mobile_uploaded</gphoto:streamId>");
      localStringBuilder2.append("</entry>");
      paramUploadTaskEntry.setRequestTemplate(localStringBuilder2.toString() + "\r\n");
      Uri.Builder localBuilder = PICASA_BASE_UPLOAD_URL.buildUpon();
      localBuilder.appendEncodedPath(str2).appendQueryParameter("caid", str5).appendQueryParameter("xmlerrors", "1");
      if (str1 != null)
        localBuilder.appendQueryParameter("evid", str1);
      if (paramBoolean)
        localBuilder.appendQueryParameter("fres", "true");
      paramUploadTaskEntry.setUrl(localBuilder.build().toString());
      bool = true;
      break;
    }
  }

  private static long getFileLengthFromContent(ContentResolver paramContentResolver, Uri paramUri)
  {
    InputStream localInputStream = null;
    try
    {
      localInputStream = paramContentResolver.openInputStream(paramUri);
      byte[] arrayOfByte = new byte[1024];
      long l = 0L;
      int j;
      for (int i = localInputStream.read(arrayOfByte); i >= 0; i = j)
      {
        l += i;
        j = localInputStream.read(arrayOfByte);
      }
      return l;
    }
    catch (Exception localException)
    {
      if ((localException instanceof RuntimeException))
        throw ((RuntimeException)localException);
    }
    finally
    {
      Utils.closeSilently(localInputStream);
    }
    throw new RuntimeException(localException);
  }

  private static long getFileLengthFromRawFdOrContent(ContentResolver paramContentResolver, Uri paramUri)
  {
    AssetFileDescriptor localAssetFileDescriptor = null;
    try
    {
      localAssetFileDescriptor = paramContentResolver.openAssetFileDescriptor(paramUri, "r");
      long l3 = localAssetFileDescriptor.getLength();
      l2 = l3;
      return l2;
    }
    catch (Exception localException)
    {
      while (true)
      {
        long l1 = getFileLengthFromContent(paramContentResolver, paramUri);
        long l2 = l1;
        if (localAssetFileDescriptor != null)
          Utils.closeSilently(localAssetFileDescriptor.getParcelFileDescriptor());
      }
    }
    finally
    {
      if (localAssetFileDescriptor != null)
        Utils.closeSilently(localAssetFileDescriptor.getParcelFileDescriptor());
    }
  }

  // ERROR //
  private static long getOptionalLong(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString, long paramLong)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore 5
    //   3: aload_0
    //   4: aload_1
    //   5: aload_2
    //   6: aconst_null
    //   7: aconst_null
    //   8: aconst_null
    //   9: invokevirtual 386	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   12: astore 5
    //   14: aload 5
    //   16: invokeinterface 391 1 0
    //   21: ifeq +32 -> 53
    //   24: aload 5
    //   26: iconst_0
    //   27: invokeinterface 395 2 0
    //   32: lstore 10
    //   34: lload 10
    //   36: lstore 8
    //   38: aload 5
    //   40: ifnull +10 -> 50
    //   43: aload 5
    //   45: invokeinterface 398 1 0
    //   50: lload 8
    //   52: lreturn
    //   53: lconst_0
    //   54: lstore 8
    //   56: goto -18 -> 38
    //   59: astore 7
    //   61: aload 5
    //   63: ifnull +10 -> 73
    //   66: aload 5
    //   68: invokeinterface 398 1 0
    //   73: lconst_0
    //   74: lstore 8
    //   76: goto -26 -> 50
    //   79: astore 6
    //   81: aload 5
    //   83: ifnull +10 -> 93
    //   86: aload 5
    //   88: invokeinterface 398 1 0
    //   93: aload 6
    //   95: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   3	34	59	java/lang/Exception
    //   3	34	79	finally
  }

  // ERROR //
  private static String getOptionalString(ContentResolver paramContentResolver, Uri paramUri, String[] paramArrayOfString)
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_3
    //   2: aload_0
    //   3: aload_1
    //   4: aload_2
    //   5: aconst_null
    //   6: aconst_null
    //   7: aconst_null
    //   8: invokevirtual 386	android/content/ContentResolver:query	(Landroid/net/Uri;[Ljava/lang/String;Ljava/lang/String;[Ljava/lang/String;Ljava/lang/String;)Landroid/database/Cursor;
    //   11: astore_3
    //   12: aload_3
    //   13: invokeinterface 391 1 0
    //   18: ifeq +29 -> 47
    //   21: aload_3
    //   22: iconst_0
    //   23: invokeinterface 401 2 0
    //   28: astore 7
    //   30: aload 7
    //   32: astore 6
    //   34: aload_3
    //   35: ifnull +9 -> 44
    //   38: aload_3
    //   39: invokeinterface 398 1 0
    //   44: aload 6
    //   46: areturn
    //   47: aconst_null
    //   48: astore 6
    //   50: goto -16 -> 34
    //   53: astore 5
    //   55: aload_3
    //   56: ifnull +9 -> 65
    //   59: aload_3
    //   60: invokeinterface 398 1 0
    //   65: aconst_null
    //   66: astore 6
    //   68: goto -24 -> 44
    //   71: astore 4
    //   73: aload_3
    //   74: ifnull +9 -> 83
    //   77: aload_3
    //   78: invokeinterface 398 1 0
    //   83: aload 4
    //   85: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   2	30	53	java/lang/Exception
    //   2	30	71	finally
  }

  private static String setContentType(ContentResolver paramContentResolver, UploadTaskEntry paramUploadTaskEntry)
  {
    Uri localUri = paramUploadTaskEntry.getContentUri();
    String str = paramContentResolver.getType(localUri);
    if (EsLog.isLoggable("iu.UploadsManager", 2))
      Log.v("iu.UploadsManager", "  contentType from resolver: " + str);
    if (str == null)
    {
      str = URLConnection.guessContentTypeFromName(localUri.toString());
      if (EsLog.isLoggable("iu.UploadsManager", 2))
        Log.v("iu.UploadsManager", "  guess contentType from url: " + str);
    }
    InputStream localInputStream;
    if (str == null)
      localInputStream = null;
    while (true)
    {
      try
      {
        localInputStream = paramContentResolver.openInputStream(localUri);
        str = URLConnection.guessContentTypeFromStream(localInputStream);
        if (EsLog.isLoggable("iu.UploadsManager", 2))
          Log.v("iu.UploadsManager", "  guess contentType from stream: " + str);
        Utils.closeSilently(localInputStream);
        if (str != null)
        {
          paramUploadTaskEntry.setMimeType(str);
          return str;
        }
      }
      catch (Throwable localThrowable)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 3))
          Log.d("iu.UploadsManager", "failed to guess content type:" + localThrowable);
        Utils.closeSilently(localInputStream);
        continue;
      }
      finally
      {
        Utils.closeSilently(localInputStream);
      }
      str = paramUploadTaskEntry.getMimeType();
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.UploadRequestHelper
 * JD-Core Version:    0.6.2
 */