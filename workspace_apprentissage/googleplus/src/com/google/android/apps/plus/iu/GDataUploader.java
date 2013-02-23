package com.google.android.apps.plus.iu;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.os.SystemClock;
import android.util.Log;
import android.util.Xml;
import android.util.Xml.Encoding;
import com.android.gallery3d.common.Fingerprint;
import com.android.gallery3d.common.Utils;
import com.google.android.apps.plus.util.EsLog;
import java.io.IOException;
import java.io.InputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.StatusLine;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.BufferedHttpEntity;
import org.apache.http.entity.StringEntity;
import org.xml.sax.Attributes;
import org.xml.sax.SAXException;
import org.xml.sax.helpers.DefaultHandler;

final class GDataUploader
  implements Uploader
{
  private static final Pattern RE_RANGE_HEADER = Pattern.compile("bytes=(\\d+)-(\\d+)");
  private static String sUserAgent;
  private Authorizer mAuthorizer;
  private Context mContext;
  private HttpClient mHttpClient;
  private Uploader.UploadProgressListener mListener;
  private UploadTaskEntry mUploadTask;
  private final UploadsDatabaseHelper mUploadsDbHelper;

  GDataUploader(Context paramContext)
  {
    this.mContext = paramContext;
    this.mHttpClient = HttpUtils.createHttpClient(getUserAgent(paramContext));
    this.mUploadsDbHelper = UploadsDatabaseHelper.getInstance(paramContext);
    this.mAuthorizer = new Authorizer(paramContext);
  }

  private HttpResponse executeWithAuthRetry(HttpUriRequest paramHttpUriRequest, String paramString1, String paramString2)
    throws ClientProtocolException, IOException, Uploader.UnauthorizedException
  {
    long l1 = SystemClock.elapsedRealtime();
    HttpResponse localHttpResponse = this.mHttpClient.execute(paramHttpUriRequest);
    MetricsUtils.incrementNetworkOpDuration(SystemClock.elapsedRealtime() - l1);
    int i = localHttpResponse.getStatusLine().getStatusCode();
    if ((i == 401) || (i == 403))
    {
      String str;
      try
      {
        str = this.mAuthorizer.getFreshAuthToken(paramString1, "lh2", paramString2);
        if (str == null)
          throw new Uploader.UnauthorizedException("null auth token");
      }
      catch (OperationCanceledException localOperationCanceledException)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 3))
          Log.d("iu.UploadsManager", "authentication canceled", localOperationCanceledException);
        throw new Uploader.UnauthorizedException(localOperationCanceledException);
      }
      catch (IOException localIOException)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 3))
          Log.d("iu.UploadsManager", "authentication failed", localIOException);
        throw localIOException;
      }
      catch (AuthenticatorException localAuthenticatorException)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", localAuthenticatorException);
        throw new Uploader.UnauthorizedException(localAuthenticatorException);
      }
      paramHttpUriRequest.setHeader("Authorization", "GoogleLogin auth=" + str);
      if (EsLog.isLoggable("iu.UploadsManager", 3))
        Log.d("iu.UploadsManager", "executeWithAuthRetry: attempt #2");
      long l2 = SystemClock.elapsedRealtime();
      localHttpResponse = this.mHttpClient.execute(paramHttpUriRequest);
      MetricsUtils.incrementNetworkOpDuration(SystemClock.elapsedRealtime() - l2);
    }
    return localHttpResponse;
  }

  private String getAuthToken(String paramString)
    throws IOException, Uploader.UnauthorizedException
  {
    try
    {
      String str = this.mAuthorizer.getAuthToken(paramString, "lh2");
      return str;
    }
    catch (OperationCanceledException localOperationCanceledException)
    {
      if (EsLog.isLoggable("iu.UploadsManager", 4))
        Log.i("iu.UploadsManager", "authentication canceled", localOperationCanceledException);
      throw new Uploader.UnauthorizedException(localOperationCanceledException);
    }
    catch (IOException localIOException)
    {
      if (EsLog.isLoggable("iu.UploadsManager", 4))
        Log.i("iu.UploadsManager", "authentication failed", localIOException);
      throw localIOException;
    }
    catch (AuthenticatorException localAuthenticatorException)
    {
      if (EsLog.isLoggable("iu.UploadsManager", 5))
        Log.w("iu.UploadsManager", localAuthenticatorException);
      throw new Uploader.UnauthorizedException(localAuthenticatorException);
    }
  }

  private static HttpEntity getEntity(HttpResponse paramHttpResponse)
    throws IOException
  {
    BufferedHttpEntity localBufferedHttpEntity = new BufferedHttpEntity(paramHttpResponse.getEntity());
    if (localBufferedHttpEntity.getContentLength() == 0L)
    {
      safeConsumeContent(localBufferedHttpEntity);
      localBufferedHttpEntity = null;
    }
    return localBufferedHttpEntity;
  }

  private MediaRecordEntry getMediaRecordEntry(UploadTaskEntry paramUploadTaskEntry, GDataResponse paramGDataResponse)
    throws Uploader.UploadException
  {
    MediaRecordEntry localMediaRecordEntry = MediaRecordEntry.fromId(this.mUploadsDbHelper.getReadableDatabase(), paramUploadTaskEntry.getMediaRecordId());
    if (localMediaRecordEntry == null)
      throw new Uploader.UploadException("could not find the media record for the uploaded task; " + paramUploadTaskEntry);
    localMediaRecordEntry.setUploadId(paramGDataResponse.photoId).setUploadUrl(paramGDataResponse.photoUrl).setUploadTime(paramGDataResponse.timestamp).setBytesUploaded(paramUploadTaskEntry.getBytesUploaded()).setState(300);
    return localMediaRecordEntry;
  }

  private static String getUserAgent(Context paramContext)
  {
    if (sUserAgent == null);
    try
    {
      PackageInfo localPackageInfo = paramContext.getPackageManager().getPackageInfo(paramContext.getPackageName(), 0);
      Object[] arrayOfObject = new Object[10];
      arrayOfObject[0] = localPackageInfo.packageName;
      arrayOfObject[1] = localPackageInfo.versionName;
      arrayOfObject[2] = Build.BRAND;
      arrayOfObject[3] = Build.DEVICE;
      arrayOfObject[4] = Build.MODEL;
      arrayOfObject[5] = Build.ID;
      arrayOfObject[6] = Build.VERSION.SDK;
      arrayOfObject[7] = Build.VERSION.RELEASE;
      arrayOfObject[8] = Build.VERSION.INCREMENTAL;
      arrayOfObject[9] = Integer.valueOf(1);
      sUserAgent = String.format("%s/%s; %s/%s/%s/%s; %s/%s/%s/%d", arrayOfObject);
      return sUserAgent;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
    }
    throw new IllegalStateException("getPackageInfo failed");
  }

  private static boolean isIncompeteStatusCode(int paramInt)
  {
    if (paramInt == 308);
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static boolean isSuccessStatusCode(int paramInt)
  {
    if ((paramInt == 200) || (paramInt == 201));
    for (boolean bool = true; ; bool = false)
      return bool;
  }

  private static HashMap<String, String> parseHeaders(String paramString)
  {
    HashMap localHashMap = new HashMap();
    String[] arrayOfString1 = paramString.split("\r\n");
    int i = arrayOfString1.length;
    for (int j = 0; j < i; j++)
    {
      String[] arrayOfString2 = arrayOfString1[j].split(":");
      if (arrayOfString2.length == 2)
        localHashMap.put(arrayOfString2[0], arrayOfString2[1]);
    }
    return localHashMap;
  }

  // ERROR //
  private static GDataQuota parseQuotaResponse(HttpEntity paramHttpEntity)
  {
    // Byte code:
    //   0: aload_0
    //   1: ifnonnull +9 -> 10
    //   4: aconst_null
    //   5: astore 7
    //   7: aload 7
    //   9: areturn
    //   10: new 221	com/google/android/apps/plus/iu/GDataUploader$GDataResponse
    //   13: dup
    //   14: iconst_0
    //   15: invokespecial 350	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:<init>	(B)V
    //   18: astore_1
    //   19: aconst_null
    //   20: astore_2
    //   21: aload_0
    //   22: invokeinterface 356 1 0
    //   27: astore_2
    //   28: aload_2
    //   29: getstatic 362	android/util/Xml$Encoding:UTF_8	Landroid/util/Xml$Encoding;
    //   32: aload_1
    //   33: invokestatic 368	android/util/Xml:parse	(Ljava/io/InputStream;Landroid/util/Xml$Encoding;Lorg/xml/sax/ContentHandler;)V
    //   36: aload_2
    //   37: ifnull +7 -> 44
    //   40: aload_2
    //   41: invokevirtual 373	java/io/InputStream:close	()V
    //   44: aload_1
    //   45: getfield 377	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:iuStats	Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;
    //   48: astore 7
    //   50: goto -43 -> 7
    //   53: astore 10
    //   55: aload_2
    //   56: ifnull -12 -> 44
    //   59: aload_2
    //   60: invokevirtual 373	java/io/InputStream:close	()V
    //   63: goto -19 -> 44
    //   66: astore 11
    //   68: goto -24 -> 44
    //   71: astore 8
    //   73: aload_2
    //   74: ifnull -30 -> 44
    //   77: aload_2
    //   78: invokevirtual 373	java/io/InputStream:close	()V
    //   81: goto -37 -> 44
    //   84: astore 9
    //   86: goto -42 -> 44
    //   89: astore 5
    //   91: aload_2
    //   92: ifnull -48 -> 44
    //   95: aload_2
    //   96: invokevirtual 373	java/io/InputStream:close	()V
    //   99: goto -55 -> 44
    //   102: astore 6
    //   104: goto -60 -> 44
    //   107: astore_3
    //   108: aload_2
    //   109: ifnull +7 -> 116
    //   112: aload_2
    //   113: invokevirtual 373	java/io/InputStream:close	()V
    //   116: aload_3
    //   117: athrow
    //   118: astore 12
    //   120: goto -76 -> 44
    //   123: astore 4
    //   125: goto -9 -> 116
    //
    // Exception table:
    //   from	to	target	type
    //   21	36	53	java/lang/IllegalStateException
    //   59	63	66	java/io/IOException
    //   21	36	71	java/io/IOException
    //   77	81	84	java/io/IOException
    //   21	36	89	org/xml/sax/SAXException
    //   95	99	102	java/io/IOException
    //   21	36	107	finally
    //   40	44	118	java/io/IOException
    //   112	116	123	java/io/IOException
  }

  private static long parseRangeHeaderEndByte(String paramString)
  {
    Matcher localMatcher;
    if (paramString != null)
    {
      localMatcher = RE_RANGE_HEADER.matcher(paramString);
      if (!localMatcher.find());
    }
    for (long l = 1L + Long.parseLong(localMatcher.group(2)); ; l = -1L)
      return l;
  }

  private static GDataResponse parseResult(HttpEntity paramHttpEntity)
    throws SAXException, IOException, Uploader.UploadException
  {
    if (paramHttpEntity == null)
      throw new Uploader.UploadException("null HttpEntity in response");
    GDataResponse localGDataResponse = new GDataResponse((byte)0);
    InputStream localInputStream = paramHttpEntity.getContent();
    try
    {
      Xml.parse(localInputStream, Xml.Encoding.UTF_8, localGDataResponse);
      localInputStream.close();
      return localGDataResponse;
    }
    finally
    {
      localInputStream.close();
    }
  }

  private void resetUpload()
  {
    this.mUploadTask.setUploadUrl(null);
    this.mUploadTask.setBytesUploaded(0L);
  }

  private MediaRecordEntry resume(InputStream paramInputStream, String paramString1, String paramString2)
    throws ClientProtocolException, IOException, Uploader.PicasaQuotaException, SAXException, Uploader.UploadException, Uploader.LocalIoException, Uploader.MediaFileChangedException, Uploader.RestartException, Uploader.UnauthorizedException
  {
    HttpPut localHttpPut = new HttpPut(this.mUploadTask.getUploadUrl());
    localHttpPut.setHeader("Content-Range", "bytes */*");
    HttpResponse localHttpResponse = executeWithAuthRetry(localHttpPut, paramString1, paramString2);
    int i = localHttpResponse.getStatusLine().getStatusCode();
    HttpEntity localHttpEntity = getEntity(localHttpResponse);
    if ((localHttpEntity == null) && (EsLog.isLoggable("iu.UploadsManager", 3)))
      Log.d("iu.UploadsManager", "  Entity: content length was 0.");
    long l;
    try
    {
      if ((!isIncompeteStatusCode(i)) || (!localHttpResponse.containsHeader("range")))
        break label217;
      Header localHeader = localHttpResponse.getFirstHeader("range");
      l = parseRangeHeaderEndByte(localHeader.getValue());
      if (l < 0L)
      {
        resetUpload();
        throw new Uploader.RestartException("negative range offset: " + localHeader);
      }
    }
    finally
    {
      safeConsumeContent(localHttpEntity);
    }
    paramInputStream.skip(l);
    paramInputStream.mark(262144);
    this.mUploadTask.setBytesUploaded(l);
    MediaRecordEntry localMediaRecordEntry2 = uploadChunks(paramInputStream, paramString1, paramString2);
    Object localObject2 = localMediaRecordEntry2;
    safeConsumeContent(localHttpEntity);
    while (true)
    {
      return localObject2;
      label217: if (!isSuccessStatusCode(i))
        break;
      GDataResponse localGDataResponse = parseResult(localHttpEntity);
      throwIfQuotaError(localGDataResponse);
      if (localGDataResponse.iuStats != null)
        InstantUploadProvider.updateQuotaSettings(this.mContext, paramString1, localGDataResponse.iuStats);
      if (EsLog.isLoggable("iu.UploadsManager", 3))
        Log.d("iu.UploadsManager", "nothing to resume; upload already complete");
      this.mUploadTask.setBytesUploaded(this.mUploadTask.getBytesTotal());
      MediaRecordEntry localMediaRecordEntry1 = getMediaRecordEntry(this.mUploadTask, localGDataResponse);
      localObject2 = localMediaRecordEntry1;
      safeConsumeContent(localHttpEntity);
    }
    if (i == 401)
      throw new Uploader.UnauthorizedException(localHttpResponse.getStatusLine().toString());
    resetUpload();
    throw new Uploader.RestartException("unexpected resume response: " + localHttpResponse.getStatusLine());
  }

  private static void safeConsumeContent(HttpEntity paramHttpEntity)
  {
    if (paramHttpEntity != null);
    try
    {
      paramHttpEntity.consumeContent();
      label10: return;
    }
    catch (IOException localIOException)
    {
      break label10;
    }
  }

  private MediaRecordEntry start(InputStream paramInputStream, Uri paramUri, String paramString1, String paramString2, String paramString3)
    throws ClientProtocolException, IOException, Uploader.PicasaQuotaException, SAXException, Uploader.UploadException, Uploader.MediaFileChangedException, Uploader.UnauthorizedException, Uploader.RestartException, Uploader.LocalIoException
  {
    HttpPost localHttpPost = new HttpPost(paramUri.toString());
    int i = paramString1.indexOf("\r\n\r\n");
    String str1;
    if (i > 0)
    {
      String str3 = paramString1.substring(0, i);
      str1 = paramString1.substring(i);
      paramString1 = str3;
    }
    while (true)
    {
      HashMap localHashMap = parseHeaders(paramString1);
      Iterator localIterator = localHashMap.keySet().iterator();
      while (localIterator.hasNext())
      {
        String str2 = (String)localIterator.next();
        localHttpPost.setHeader(str2, (String)localHashMap.get(str2));
      }
      str1 = null;
    }
    if (str1 != null)
    {
      StringEntity localStringEntity = new StringEntity(str1);
      localStringEntity.setContentType(null);
      localHttpPost.setEntity(localStringEntity);
    }
    HttpResponse localHttpResponse = executeWithAuthRetry(localHttpPost, paramString2, paramString3);
    HttpEntity localHttpEntity = getEntity(localHttpResponse);
    int j = localHttpResponse.getStatusLine().getStatusCode();
    try
    {
      if (isSuccessStatusCode(j))
      {
        if (localHttpEntity != null)
          throwIfQuotaError(parseResult(localHttpEntity));
        Header localHeader = localHttpResponse.getFirstHeader("Location");
        this.mUploadTask.setUploadUrl(localHeader.getValue());
        this.mUploadTask.setBytesUploaded(0L);
        MediaRecordEntry localMediaRecordEntry = uploadChunks(paramInputStream, paramString2, paramString3);
        return localMediaRecordEntry;
      }
      if (j == 400)
        throw new Uploader.UploadException("upload failed (bad payload, file too large) " + localHttpResponse.getStatusLine());
    }
    finally
    {
      safeConsumeContent(localHttpEntity);
    }
    if (j == 401)
      throw new Uploader.UnauthorizedException(localHttpResponse.getStatusLine().toString());
    if ((j >= 500) && (j < 600))
      throw new Uploader.RestartException("upload transient error:" + localHttpResponse.getStatusLine());
    throw new Uploader.UploadException(localHttpResponse.getStatusLine().toString());
  }

  private static void throwIfQuotaError(GDataResponse paramGDataResponse)
    throws Uploader.PicasaQuotaException
  {
    if ((paramGDataResponse != null) && ("LimitQuota".equals(paramGDataResponse.errorCode)))
      throw new Uploader.PicasaQuotaException(paramGDataResponse.errorCode);
  }

  // ERROR //
  private MediaRecordEntry uploadChunks(InputStream paramInputStream, String paramString1, String paramString2)
    throws ClientProtocolException, IOException, Uploader.PicasaQuotaException, SAXException, Uploader.UploadException, Uploader.MediaFileChangedException, Uploader.RestartException, Uploader.LocalIoException, Uploader.UnauthorizedException
  {
    // Byte code:
    //   0: ldc_w 470
    //   3: newarray byte
    //   5: astore 4
    //   7: aload_0
    //   8: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   11: invokevirtual 245	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesUploaded	()J
    //   14: aload_0
    //   15: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   18: invokevirtual 496	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesTotal	()J
    //   21: lcmp
    //   22: ifge +917 -> 939
    //   25: aload_0
    //   26: getfield 579	com/google/android/apps/plus/iu/GDataUploader:mListener	Lcom/google/android/apps/plus/iu/Uploader$UploadProgressListener;
    //   29: ifnull +16 -> 45
    //   32: aload_0
    //   33: getfield 579	com/google/android/apps/plus/iu/GDataUploader:mListener	Lcom/google/android/apps/plus/iu/Uploader$UploadProgressListener;
    //   36: aload_0
    //   37: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   40: invokeinterface 585 2 0
    //   45: aload_0
    //   46: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   49: invokevirtual 588	com/google/android/apps/plus/iu/UploadTaskEntry:isUploading	()Z
    //   52: ifne +9 -> 61
    //   55: aconst_null
    //   56: astore 33
    //   58: aload 33
    //   60: areturn
    //   61: aload_0
    //   62: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   65: invokevirtual 245	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesUploaded	()J
    //   68: lstore 5
    //   70: aload_0
    //   71: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   74: invokevirtual 496	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesTotal	()J
    //   77: lload 5
    //   79: lsub
    //   80: l2i
    //   81: istore 7
    //   83: iload 7
    //   85: ldc_w 470
    //   88: if_icmpgt +74 -> 162
    //   91: iconst_1
    //   92: istore 8
    //   94: iload 8
    //   96: ifne +8 -> 104
    //   99: ldc_w 470
    //   102: istore 7
    //   104: aload_1
    //   105: ldc_w 470
    //   108: invokevirtual 474	java/io/InputStream:mark	(I)V
    //   111: iconst_0
    //   112: istore 9
    //   114: iload 9
    //   116: iload 7
    //   118: if_icmpge +50 -> 168
    //   121: iload 9
    //   123: iconst_0
    //   124: iadd
    //   125: istore 40
    //   127: iload 7
    //   129: iload 9
    //   131: isub
    //   132: istore 41
    //   134: aload_1
    //   135: aload 4
    //   137: iload 40
    //   139: iload 41
    //   141: invokevirtual 592	java/io/InputStream:read	([BII)I
    //   144: istore 44
    //   146: iload 44
    //   148: iconst_m1
    //   149: if_icmpeq +19 -> 168
    //   152: iload 9
    //   154: iload 44
    //   156: iadd
    //   157: istore 9
    //   159: goto -45 -> 114
    //   162: iconst_0
    //   163: istore 8
    //   165: goto -71 -> 94
    //   168: iload 8
    //   170: ifne +11 -> 181
    //   173: iload 9
    //   175: ldc_w 470
    //   178: if_icmpeq +215 -> 393
    //   181: iconst_1
    //   182: newarray long
    //   184: astore 10
    //   186: aload_0
    //   187: getfield 40	com/google/android/apps/plus/iu/GDataUploader:mContext	Landroid/content/Context;
    //   190: invokevirtual 596	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   193: aload_0
    //   194: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   197: invokevirtual 600	com/google/android/apps/plus/iu/UploadTaskEntry:getContentUri	()Landroid/net/Uri;
    //   200: invokevirtual 606	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   203: aload 10
    //   205: invokestatic 612	com/android/gallery3d/common/Fingerprint:fromInputStream	(Ljava/io/InputStream;[J)Lcom/android/gallery3d/common/Fingerprint;
    //   208: astore 11
    //   210: aload 11
    //   212: aload_0
    //   213: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   216: invokevirtual 616	com/google/android/apps/plus/iu/UploadTaskEntry:getFingerprint	()Lcom/android/gallery3d/common/Fingerprint;
    //   219: invokevirtual 617	com/android/gallery3d/common/Fingerprint:equals	(Ljava/lang/Object;)Z
    //   222: ifne +171 -> 393
    //   225: aload_0
    //   226: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   229: invokevirtual 600	com/google/android/apps/plus/iu/UploadTaskEntry:getContentUri	()Landroid/net/Uri;
    //   232: invokevirtual 511	android/net/Uri:toString	()Ljava/lang/String;
    //   235: astore 36
    //   237: aload_0
    //   238: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   241: aload 11
    //   243: invokevirtual 621	com/google/android/apps/plus/iu/UploadTaskEntry:setFingerprint	(Lcom/android/gallery3d/common/Fingerprint;)V
    //   246: aload_0
    //   247: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   250: invokevirtual 429	com/google/android/apps/plus/iu/UploadTaskEntry:getUploadUrl	()Ljava/lang/String;
    //   253: astore 37
    //   255: aload_0
    //   256: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   259: aconst_null
    //   260: invokevirtual 412	com/google/android/apps/plus/iu/UploadTaskEntry:setUploadUrl	(Ljava/lang/String;)V
    //   263: aload_0
    //   264: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   267: lconst_0
    //   268: invokevirtual 414	com/google/android/apps/plus/iu/UploadTaskEntry:setBytesUploaded	(J)V
    //   271: aload_0
    //   272: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   275: aload 10
    //   277: iconst_0
    //   278: laload
    //   279: invokevirtual 624	com/google/android/apps/plus/iu/UploadTaskEntry:setBytesTotal	(J)V
    //   282: aload_0
    //   283: getfield 579	com/google/android/apps/plus/iu/GDataUploader:mListener	Lcom/google/android/apps/plus/iu/Uploader$UploadProgressListener;
    //   286: ifnull +16 -> 302
    //   289: aload_0
    //   290: getfield 579	com/google/android/apps/plus/iu/GDataUploader:mListener	Lcom/google/android/apps/plus/iu/Uploader$UploadProgressListener;
    //   293: aload_0
    //   294: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   297: invokeinterface 627 2 0
    //   302: new 422	com/google/android/apps/plus/iu/Uploader$MediaFileChangedException
    //   305: dup
    //   306: new 148	java/lang/StringBuilder
    //   309: dup
    //   310: ldc_w 629
    //   313: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   316: aload 36
    //   318: invokevirtual 155	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   321: ldc_w 631
    //   324: invokevirtual 155	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   327: aload 37
    //   329: invokevirtual 155	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   332: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   335: invokespecial 632	com/google/android/apps/plus/iu/Uploader$MediaFileChangedException:<init>	(Ljava/lang/String;)V
    //   338: athrow
    //   339: astore 42
    //   341: new 420	com/google/android/apps/plus/iu/Uploader$LocalIoException
    //   344: dup
    //   345: aload 42
    //   347: invokespecial 633	com/google/android/apps/plus/iu/Uploader$LocalIoException:<init>	(Ljava/lang/Throwable;)V
    //   350: astore 43
    //   352: aload 43
    //   354: athrow
    //   355: astore 38
    //   357: ldc 121
    //   359: iconst_3
    //   360: invokestatic 127	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   363: ifeq -61 -> 302
    //   366: ldc 121
    //   368: new 148	java/lang/StringBuilder
    //   371: dup
    //   372: ldc_w 635
    //   375: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   378: aload 38
    //   380: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   383: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   386: invokestatic 170	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   389: pop
    //   390: goto -88 -> 302
    //   393: ldc 121
    //   395: iconst_4
    //   396: invokestatic 127	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   399: ifeq +29 -> 428
    //   402: ldc 121
    //   404: new 148	java/lang/StringBuilder
    //   407: dup
    //   408: ldc_w 637
    //   411: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   414: aload_0
    //   415: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   418: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   421: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   424: invokestatic 639	android/util/Log:i	(Ljava/lang/String;Ljava/lang/String;)I
    //   427: pop
    //   428: aload_0
    //   429: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   432: invokevirtual 429	com/google/android/apps/plus/iu/UploadTaskEntry:getUploadUrl	()Ljava/lang/String;
    //   435: astore 12
    //   437: aload_0
    //   438: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   441: invokevirtual 642	com/google/android/apps/plus/iu/UploadTaskEntry:getMimeType	()Ljava/lang/String;
    //   444: astore 13
    //   446: aload_0
    //   447: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   450: invokevirtual 496	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesTotal	()J
    //   453: lstore 14
    //   455: new 426	org/apache/http/client/methods/HttpPut
    //   458: dup
    //   459: aload 12
    //   461: invokespecial 430	org/apache/http/client/methods/HttpPut:<init>	(Ljava/lang/String;)V
    //   464: astore 16
    //   466: lload 5
    //   468: iload 7
    //   470: i2l
    //   471: ladd
    //   472: lconst_1
    //   473: lsub
    //   474: lstore 17
    //   476: aload 16
    //   478: ldc_w 432
    //   481: new 148	java/lang/StringBuilder
    //   484: dup
    //   485: ldc_w 644
    //   488: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   491: lload 5
    //   493: invokevirtual 647	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   496: ldc_w 649
    //   499: invokevirtual 155	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   502: lload 17
    //   504: invokevirtual 647	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   507: ldc_w 651
    //   510: invokevirtual 155	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   513: lload 14
    //   515: invokevirtual 647	java/lang/StringBuilder:append	(J)Ljava/lang/StringBuilder;
    //   518: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   521: invokevirtual 435	org/apache/http/client/methods/HttpPut:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   524: aload 16
    //   526: ldc_w 653
    //   529: aload 13
    //   531: invokevirtual 435	org/apache/http/client/methods/HttpPut:setHeader	(Ljava/lang/String;Ljava/lang/String;)V
    //   534: new 655	java/io/ByteArrayInputStream
    //   537: dup
    //   538: aload 4
    //   540: iconst_0
    //   541: iload 7
    //   543: invokespecial 658	java/io/ByteArrayInputStream:<init>	([BII)V
    //   546: astore 19
    //   548: new 660	org/apache/http/entity/InputStreamEntity
    //   551: dup
    //   552: aload 19
    //   554: iload 7
    //   556: i2l
    //   557: invokespecial 663	org/apache/http/entity/InputStreamEntity:<init>	(Ljava/io/InputStream;J)V
    //   560: astore 20
    //   562: aload 20
    //   564: aconst_null
    //   565: invokevirtual 664	org/apache/http/entity/InputStreamEntity:setContentType	(Ljava/lang/String;)V
    //   568: aload 16
    //   570: aload 20
    //   572: invokevirtual 665	org/apache/http/client/methods/HttpPut:setEntity	(Lorg/apache/http/HttpEntity;)V
    //   575: aload_0
    //   576: aload 16
    //   578: aload_2
    //   579: aload_3
    //   580: invokespecial 437	com/google/android/apps/plus/iu/GDataUploader:executeWithAuthRetry	(Lorg/apache/http/client/methods/HttpUriRequest;Ljava/lang/String;Ljava/lang/String;)Lorg/apache/http/HttpResponse;
    //   583: astore 21
    //   585: aload 21
    //   587: invokeinterface 102 1 0
    //   592: invokeinterface 108 1 0
    //   597: istore 23
    //   599: iload 23
    //   601: invokestatic 479	com/google/android/apps/plus/iu/GDataUploader:isSuccessStatusCode	(I)Z
    //   604: ifeq +104 -> 708
    //   607: aload 21
    //   609: invokestatic 439	com/google/android/apps/plus/iu/GDataUploader:getEntity	(Lorg/apache/http/HttpResponse;)Lorg/apache/http/HttpEntity;
    //   612: invokestatic 481	com/google/android/apps/plus/iu/GDataUploader:parseResult	(Lorg/apache/http/HttpEntity;)Lcom/google/android/apps/plus/iu/GDataUploader$GDataResponse;
    //   615: astore 31
    //   617: aload 31
    //   619: invokestatic 485	com/google/android/apps/plus/iu/GDataUploader:throwIfQuotaError	(Lcom/google/android/apps/plus/iu/GDataUploader$GDataResponse;)V
    //   622: aload 31
    //   624: getfield 377	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:iuStats	Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;
    //   627: ifnull +16 -> 643
    //   630: aload_0
    //   631: getfield 40	com/google/android/apps/plus/iu/GDataUploader:mContext	Landroid/content/Context;
    //   634: aload_2
    //   635: aload 31
    //   637: getfield 377	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:iuStats	Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;
    //   640: invokestatic 491	com/google/android/apps/plus/iu/InstantUploadProvider:updateQuotaSettings	(Landroid/content/Context;Ljava/lang/String;Lcom/google/android/apps/plus/iu/GDataUploader$GDataQuota;)V
    //   643: ldc 121
    //   645: iconst_3
    //   646: invokestatic 127	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   649: ifeq +12 -> 661
    //   652: ldc 121
    //   654: ldc_w 667
    //   657: invokestatic 170	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   660: pop
    //   661: aload_0
    //   662: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   665: aload_0
    //   666: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   669: invokevirtual 496	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesTotal	()J
    //   672: invokevirtual 414	com/google/android/apps/plus/iu/UploadTaskEntry:setBytesUploaded	(J)V
    //   675: lconst_1
    //   676: invokestatic 670	com/google/android/apps/plus/iu/MetricsUtils:incrementNetworkOpCount	(J)V
    //   679: aload_0
    //   680: aload_0
    //   681: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   684: aload 31
    //   686: invokespecial 498	com/google/android/apps/plus/iu/GDataUploader:getMediaRecordEntry	(Lcom/google/android/apps/plus/iu/UploadTaskEntry;Lcom/google/android/apps/plus/iu/GDataUploader$GDataResponse;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   689: astore 32
    //   691: aload 32
    //   693: astore 33
    //   695: aload 21
    //   697: invokeinterface 185 1 0
    //   702: invokestatic 194	com/google/android/apps/plus/iu/GDataUploader:safeConsumeContent	(Lorg/apache/http/HttpEntity;)V
    //   705: goto -647 -> 58
    //   708: iload 23
    //   710: invokestatic 443	com/google/android/apps/plus/iu/GDataUploader:isIncompeteStatusCode	(I)Z
    //   713: ifeq +122 -> 835
    //   716: aload 21
    //   718: ldc_w 445
    //   721: invokeinterface 453 2 0
    //   726: astore 24
    //   728: aload 24
    //   730: ifnull +48 -> 778
    //   733: aload 24
    //   735: invokeinterface 458 1 0
    //   740: invokestatic 460	com/google/android/apps/plus/iu/GDataUploader:parseRangeHeaderEndByte	(Ljava/lang/String;)J
    //   743: lstore 25
    //   745: lload 25
    //   747: lconst_0
    //   748: lcmp
    //   749: ifge +37 -> 786
    //   752: new 198	com/google/android/apps/plus/iu/Uploader$UploadException
    //   755: dup
    //   756: ldc_w 672
    //   759: invokespecial 219	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
    //   762: athrow
    //   763: astore 22
    //   765: aload 21
    //   767: invokeinterface 185 1 0
    //   772: invokestatic 194	com/google/android/apps/plus/iu/GDataUploader:safeConsumeContent	(Lorg/apache/http/HttpEntity;)V
    //   775: aload 22
    //   777: athrow
    //   778: ldc2_w 399
    //   781: lstore 25
    //   783: goto -38 -> 745
    //   786: lload 25
    //   788: lload 5
    //   790: iload 7
    //   792: i2l
    //   793: ladd
    //   794: lcmp
    //   795: ifge +14 -> 809
    //   798: aload_1
    //   799: invokevirtual 675	java/io/InputStream:reset	()V
    //   802: aload_1
    //   803: lload 25
    //   805: invokevirtual 469	java/io/InputStream:skip	(J)J
    //   808: pop2
    //   809: lload 25
    //   811: lstore 27
    //   813: aload_0
    //   814: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   817: lload 27
    //   819: invokevirtual 414	com/google/android/apps/plus/iu/UploadTaskEntry:setBytesUploaded	(J)V
    //   822: aload 21
    //   824: invokeinterface 185 1 0
    //   829: invokestatic 194	com/google/android/apps/plus/iu/GDataUploader:safeConsumeContent	(Lorg/apache/http/HttpEntity;)V
    //   832: goto -825 -> 7
    //   835: iload 23
    //   837: sipush 400
    //   840: if_icmpne +34 -> 874
    //   843: new 198	com/google/android/apps/plus/iu/Uploader$UploadException
    //   846: dup
    //   847: new 148	java/lang/StringBuilder
    //   850: dup
    //   851: ldc_w 563
    //   854: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   857: aload 21
    //   859: invokeinterface 102 1 0
    //   864: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   867: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   870: invokespecial 219	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
    //   873: athrow
    //   874: iload 23
    //   876: sipush 500
    //   879: if_icmplt +42 -> 921
    //   882: iload 23
    //   884: sipush 600
    //   887: if_icmpge +34 -> 921
    //   890: new 424	com/google/android/apps/plus/iu/Uploader$RestartException
    //   893: dup
    //   894: new 148	java/lang/StringBuilder
    //   897: dup
    //   898: ldc_w 677
    //   901: invokespecial 151	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   904: aload 21
    //   906: invokeinterface 102 1 0
    //   911: invokevirtual 218	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
    //   914: invokevirtual 159	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   917: invokespecial 465	com/google/android/apps/plus/iu/Uploader$RestartException:<init>	(Ljava/lang/String;)V
    //   920: athrow
    //   921: new 198	com/google/android/apps/plus/iu/Uploader$UploadException
    //   924: dup
    //   925: aload 21
    //   927: invokeinterface 102 1 0
    //   932: invokevirtual 499	java/lang/Object:toString	()Ljava/lang/String;
    //   935: invokespecial 219	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
    //   938: athrow
    //   939: new 198	com/google/android/apps/plus/iu/Uploader$UploadException
    //   942: dup
    //   943: ldc_w 679
    //   946: invokespecial 219	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
    //   949: athrow
    //
    // Exception table:
    //   from	to	target	type
    //   134	146	339	java/io/IOException
    //   282	302	355	java/lang/Throwable
    //   585	691	763	finally
    //   708	763	763	finally
    //   798	822	763	finally
    //   843	939	763	finally
  }

  public final void close()
  {
    this.mHttpClient = null;
    this.mAuthorizer = null;
  }

  final GDataQuota getQuota(String paramString)
  {
    HttpEntity localHttpEntity = null;
    try
    {
      String str = getAuthToken(paramString);
      HttpGet localHttpGet = new HttpGet("https://picasaweb.google.com/data/quotainfo");
      localHttpGet.setHeader("User-Agent", Utils.getUserAgent(this.mContext));
      localHttpGet.setHeader("GData-Version", "3.0");
      localHttpEntity = null;
      if (str != null)
        localHttpGet.setHeader("Authorization", "GoogleLogin auth=" + str);
      HttpResponse localHttpResponse = executeWithAuthRetry(localHttpGet, paramString, str);
      StatusLine localStatusLine = localHttpResponse.getStatusLine();
      localHttpEntity = localHttpResponse.getEntity();
      GDataQuota localGDataQuota2;
      if (isSuccessStatusCode(localStatusLine.getStatusCode()))
        localGDataQuota2 = parseQuotaResponse(localHttpEntity);
      for (GDataQuota localGDataQuota1 = localGDataQuota2; ; localGDataQuota1 = null)
      {
        return localGDataQuota1;
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", "Couldn't get quota info " + localStatusLine);
        safeConsumeContent(localHttpEntity);
      }
    }
    catch (Uploader.UnauthorizedException localUnauthorizedException)
    {
      while (true)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", "Unauthorized to get quota", localUnauthorizedException);
        safeConsumeContent(localHttpEntity);
      }
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 5))
          Log.w("iu.UploadsManager", "IOException getting quota", localIOException);
        safeConsumeContent(localHttpEntity);
      }
    }
    catch (Throwable localThrowable)
    {
      while (true)
      {
        if (EsLog.isLoggable("iu.UploadsManager", 6))
          Log.e("iu.UploadsManager", "Error getting quota", localThrowable);
        safeConsumeContent(localHttpEntity);
      }
    }
    finally
    {
      safeConsumeContent(localHttpEntity);
    }
  }

  // ERROR //
  public final MediaRecordEntry upload(UploadTaskEntry paramUploadTaskEntry, Uploader.UploadProgressListener paramUploadProgressListener)
    throws IOException, Uploader.UploadException, Uploader.RestartException, Uploader.LocalIoException, Uploader.MediaFileChangedException, Uploader.MediaFileUnavailableException, Uploader.UnauthorizedException, Uploader.PicasaQuotaException
  {
    // Byte code:
    //   0: aload_1
    //   1: invokevirtual 496	com/google/android/apps/plus/iu/UploadTaskEntry:getBytesTotal	()J
    //   4: lconst_0
    //   5: lcmp
    //   6: ifgt +21 -> 27
    //   9: new 718	com/google/android/apps/plus/iu/Uploader$MediaFileUnavailableException
    //   12: dup
    //   13: new 722	java/lang/IllegalArgumentException
    //   16: dup
    //   17: ldc_w 724
    //   20: invokespecial 725	java/lang/IllegalArgumentException:<init>	(Ljava/lang/String;)V
    //   23: invokespecial 726	com/google/android/apps/plus/iu/Uploader$MediaFileUnavailableException:<init>	(Ljava/lang/Throwable;)V
    //   26: athrow
    //   27: aload_0
    //   28: aload_1
    //   29: putfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   32: aload_0
    //   33: aload_2
    //   34: putfield 579	com/google/android/apps/plus/iu/GDataUploader:mListener	Lcom/google/android/apps/plus/iu/Uploader$UploadProgressListener;
    //   37: aload_0
    //   38: aload_0
    //   39: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   42: invokevirtual 729	com/google/android/apps/plus/iu/UploadTaskEntry:getAccount	()Ljava/lang/String;
    //   45: invokespecial 683	com/google/android/apps/plus/iu/GDataUploader:getAuthToken	(Ljava/lang/String;)Ljava/lang/String;
    //   48: astore_3
    //   49: aload_0
    //   50: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   53: invokevirtual 732	com/google/android/apps/plus/iu/UploadTaskEntry:getRequestTemplate	()Ljava/lang/String;
    //   56: astore 4
    //   58: aload_3
    //   59: ifnonnull +89 -> 148
    //   62: aload 4
    //   64: ldc_w 734
    //   67: ldc_w 736
    //   70: invokevirtual 739	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   73: astore 5
    //   75: new 741	java/io/BufferedInputStream
    //   78: dup
    //   79: aload_0
    //   80: getfield 40	com/google/android/apps/plus/iu/GDataUploader:mContext	Landroid/content/Context;
    //   83: invokevirtual 596	android/content/Context:getContentResolver	()Landroid/content/ContentResolver;
    //   86: aload_1
    //   87: invokevirtual 600	com/google/android/apps/plus/iu/UploadTaskEntry:getContentUri	()Landroid/net/Uri;
    //   90: invokevirtual 606	android/content/ContentResolver:openInputStream	(Landroid/net/Uri;)Ljava/io/InputStream;
    //   93: invokespecial 744	java/io/BufferedInputStream:<init>	(Ljava/io/InputStream;)V
    //   96: astore 6
    //   98: aload_0
    //   99: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   102: invokevirtual 429	com/google/android/apps/plus/iu/UploadTaskEntry:getUploadUrl	()Ljava/lang/String;
    //   105: invokestatic 750	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
    //   108: ifeq +54 -> 162
    //   111: aload_0
    //   112: aload 6
    //   114: aload_0
    //   115: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   118: invokevirtual 753	com/google/android/apps/plus/iu/UploadTaskEntry:getUrl	()Landroid/net/Uri;
    //   121: aload 5
    //   123: aload_0
    //   124: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   127: invokevirtual 729	com/google/android/apps/plus/iu/UploadTaskEntry:getAccount	()Ljava/lang/String;
    //   130: aload_3
    //   131: invokespecial 755	com/google/android/apps/plus/iu/GDataUploader:start	(Ljava/io/InputStream;Landroid/net/Uri;Ljava/lang/String;Ljava/lang/String;Ljava/lang/String;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   134: astore 14
    //   136: aload 14
    //   138: astore 13
    //   140: aload 6
    //   142: invokestatic 759	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   145: aload 13
    //   147: areturn
    //   148: aload 4
    //   150: ldc_w 761
    //   153: aload_3
    //   154: invokevirtual 739	java/lang/String:replaceAll	(Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
    //   157: astore 5
    //   159: goto -84 -> 75
    //   162: aload_0
    //   163: aload 6
    //   165: aload_0
    //   166: getfield 410	com/google/android/apps/plus/iu/GDataUploader:mUploadTask	Lcom/google/android/apps/plus/iu/UploadTaskEntry;
    //   169: invokevirtual 729	com/google/android/apps/plus/iu/UploadTaskEntry:getAccount	()Ljava/lang/String;
    //   172: aload_3
    //   173: invokespecial 763	com/google/android/apps/plus/iu/GDataUploader:resume	(Ljava/io/InputStream;Ljava/lang/String;Ljava/lang/String;)Lcom/google/android/apps/plus/iu/MediaRecordEntry;
    //   176: astore 12
    //   178: aload 12
    //   180: astore 13
    //   182: aload 6
    //   184: invokestatic 759	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   187: goto -42 -> 145
    //   190: astore 7
    //   192: aconst_null
    //   193: astore 6
    //   195: new 72	java/io/IOException
    //   198: dup
    //   199: aload 7
    //   201: invokevirtual 764	org/apache/http/client/ClientProtocolException:toString	()Ljava/lang/String;
    //   204: invokespecial 765	java/io/IOException:<init>	(Ljava/lang/String;)V
    //   207: athrow
    //   208: astore 8
    //   210: aload 6
    //   212: invokestatic 759	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   215: aload 8
    //   217: athrow
    //   218: astore 9
    //   220: aconst_null
    //   221: astore 6
    //   223: new 420	com/google/android/apps/plus/iu/Uploader$LocalIoException
    //   226: dup
    //   227: aload 9
    //   229: invokespecial 633	com/google/android/apps/plus/iu/Uploader$LocalIoException:<init>	(Ljava/lang/Throwable;)V
    //   232: athrow
    //   233: ldc 121
    //   235: bipush 6
    //   237: invokestatic 127	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   240: ifeq +14 -> 254
    //   243: ldc 121
    //   245: ldc_w 767
    //   248: aload 10
    //   250: invokestatic 714	android/util/Log:e	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   253: pop
    //   254: new 198	com/google/android/apps/plus/iu/Uploader$UploadException
    //   257: dup
    //   258: ldc_w 767
    //   261: aload 10
    //   263: invokespecial 770	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;Ljava/lang/Throwable;)V
    //   266: athrow
    //   267: astore 8
    //   269: aconst_null
    //   270: astore 6
    //   272: goto -62 -> 210
    //   275: astore 10
    //   277: goto -44 -> 233
    //   280: astore 9
    //   282: goto -59 -> 223
    //   285: astore 7
    //   287: goto -92 -> 195
    //   290: astore 10
    //   292: aconst_null
    //   293: astore 6
    //   295: goto -62 -> 233
    //
    // Exception table:
    //   from	to	target	type
    //   75	98	190	org/apache/http/client/ClientProtocolException
    //   98	136	208	finally
    //   162	178	208	finally
    //   195	208	208	finally
    //   223	267	208	finally
    //   75	98	218	java/io/FileNotFoundException
    //   75	98	267	finally
    //   98	136	275	org/xml/sax/SAXException
    //   162	178	275	org/xml/sax/SAXException
    //   98	136	280	java/io/FileNotFoundException
    //   162	178	280	java/io/FileNotFoundException
    //   98	136	285	org/apache/http/client/ClientProtocolException
    //   162	178	285	org/apache/http/client/ClientProtocolException
    //   75	98	290	org/xml/sax/SAXException
  }

  public static final class GDataQuota
  {
    boolean disableFullRes = false;
    long quotaLimit = -1L;
    long quotaUsed = -1L;

    public final String toString()
    {
      return "[GDataIUStats; limit: " + this.quotaLimit + ", used: " + this.quotaUsed + ", low quota? " + this.disableFullRes + "]";
    }
  }

  private static final class GDataResponse extends DefaultHandler
  {
    String errorCode;
    Fingerprint fingerprint;
    GDataUploader.GDataQuota iuStats;
    private HashMap<String, StringBuilder> mMap = new HashMap();
    private ArrayList<String> mStreamIdList = new ArrayList();
    private StringBuilder mText;
    long photoId;
    String photoUrl;
    long timestamp;

    private static GDataUploader.GDataQuota getIUStatsAttrs(Attributes paramAttributes)
    {
      GDataUploader.GDataQuota localGDataQuota = new GDataUploader.GDataQuota();
      int i = paramAttributes.getLength();
      int j = 0;
      while (true)
      {
        String str;
        if (j < i)
        {
          str = paramAttributes.getQName(j);
          if (!"quotaLimitMB".contentEquals(str));
        }
        try
        {
          localGDataQuota.quotaLimit = Long.parseLong(paramAttributes.getValue(j));
          while (true)
          {
            label55: j++;
            break;
            if ("quotaUsedMB".contentEquals(str))
              try
              {
                localGDataQuota.quotaUsed = Long.parseLong(paramAttributes.getValue(j));
              }
              catch (NumberFormatException localNumberFormatException1)
              {
              }
            else if ("disableFullRes".contentEquals(str))
              localGDataQuota.disableFullRes = Boolean.parseBoolean(paramAttributes.getValue(j));
          }
          return localGDataQuota;
        }
        catch (NumberFormatException localNumberFormatException2)
        {
          break label55;
        }
      }
    }

    public final void characters(char[] paramArrayOfChar, int paramInt1, int paramInt2)
    {
      if (this.mText != null)
        this.mText.append(paramArrayOfChar, paramInt1, paramInt2);
    }

    public final void endElement(String paramString1, String paramString2, String paramString3)
    {
      if (("gphoto:streamId".contentEquals(paramString3)) && (this.mText.length() > 0))
        this.mStreamIdList.add(this.mText.toString());
      this.mText = null;
    }

    public final void startDocument()
    {
      this.mMap.clear();
      this.mMap.put("code", new StringBuilder());
      this.mMap.put("gphoto:id", new StringBuilder());
      this.mMap.put("gphoto:size", new StringBuilder());
      this.mMap.put("gphoto:streamId", new StringBuilder());
      this.mMap.put("gphoto:timestamp", new StringBuilder());
      this.photoUrl = "";
      this.mStreamIdList.clear();
    }

    public final void startElement(String paramString1, String paramString2, String paramString3, Attributes paramAttributes)
    {
      this.mText = ((StringBuilder)this.mMap.get(paramString3));
      int j;
      String str;
      if (this.mText == null)
        if ("media:content".contentEquals(paramString3))
        {
          int i = paramAttributes.getLength();
          j = 0;
          if (j < i)
            if ("url".contentEquals(paramAttributes.getQName(j)))
            {
              str = paramAttributes.getValue(j);
              label78: this.photoUrl = str;
            }
        }
      while (true)
      {
        return;
        j++;
        break;
        str = "";
        break label78;
        if ("gphoto:iuStats".contentEquals(paramString3))
        {
          this.iuStats = getIUStatsAttrs(paramAttributes);
          continue;
          this.mText.setLength(0);
        }
      }
    }

    // ERROR //
    public final void validateResult()
      throws Uploader.UploadException
    {
      // Byte code:
      //   0: aload_0
      //   1: aload_0
      //   2: getfield 31	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mMap	Ljava/util/HashMap;
      //   5: ldc 122
      //   7: invokevirtual 144	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   10: checkcast 97	java/lang/StringBuilder
      //   13: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   16: putfield 163	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:errorCode	Ljava/lang/String;
      //   19: aload_0
      //   20: aload_0
      //   21: getfield 31	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mMap	Ljava/util/HashMap;
      //   24: ldc 129
      //   26: invokevirtual 144	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   29: checkcast 97	java/lang/StringBuilder
      //   32: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   35: invokestatic 72	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   38: putfield 165	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:photoId	J
      //   41: aload_0
      //   42: aload_0
      //   43: getfield 31	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mMap	Ljava/util/HashMap;
      //   46: ldc 133
      //   48: invokevirtual 144	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   51: checkcast 97	java/lang/StringBuilder
      //   54: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   57: invokestatic 72	java/lang/Long:parseLong	(Ljava/lang/String;)J
      //   60: putfield 167	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:timestamp	J
      //   63: aload_0
      //   64: getfield 137	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:photoUrl	Ljava/lang/String;
      //   67: invokestatic 172	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   70: ifeq +79 -> 149
      //   73: new 161	com/google/android/apps/plus/iu/Uploader$UploadException
      //   76: dup
      //   77: ldc 174
      //   79: invokespecial 177	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
      //   82: athrow
      //   83: astore_1
      //   84: new 161	com/google/android/apps/plus/iu/Uploader$UploadException
      //   87: dup
      //   88: new 97	java/lang/StringBuilder
      //   91: dup
      //   92: ldc 179
      //   94: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   97: aload_0
      //   98: getfield 31	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mMap	Ljava/util/HashMap;
      //   101: ldc 129
      //   103: invokevirtual 144	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   106: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   109: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   112: invokespecial 177	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
      //   115: athrow
      //   116: astore_2
      //   117: new 161	com/google/android/apps/plus/iu/Uploader$UploadException
      //   120: dup
      //   121: new 97	java/lang/StringBuilder
      //   124: dup
      //   125: ldc 185
      //   127: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   130: aload_0
      //   131: getfield 31	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mMap	Ljava/util/HashMap;
      //   134: ldc 133
      //   136: invokevirtual 144	java/util/HashMap:get	(Ljava/lang/Object;)Ljava/lang/Object;
      //   139: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   142: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   145: invokespecial 177	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
      //   148: athrow
      //   149: aload_0
      //   150: aload_0
      //   151: getfield 36	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mStreamIdList	Ljava/util/ArrayList;
      //   154: invokestatic 191	com/android/gallery3d/common/Fingerprint:extractFingerprint	(Ljava/util/List;)Lcom/android/gallery3d/common/Fingerprint;
      //   157: putfield 193	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:fingerprint	Lcom/android/gallery3d/common/Fingerprint;
      //   160: aload_0
      //   161: getfield 193	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:fingerprint	Lcom/android/gallery3d/common/Fingerprint;
      //   164: ifnonnull +30 -> 194
      //   167: new 161	com/google/android/apps/plus/iu/Uploader$UploadException
      //   170: dup
      //   171: new 97	java/lang/StringBuilder
      //   174: dup
      //   175: ldc 195
      //   177: invokespecial 180	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
      //   180: aload_0
      //   181: getfield 36	com/google/android/apps/plus/iu/GDataUploader$GDataResponse:mStreamIdList	Ljava/util/ArrayList;
      //   184: invokevirtual 183	java/lang/StringBuilder:append	(Ljava/lang/Object;)Ljava/lang/StringBuilder;
      //   187: invokevirtual 112	java/lang/StringBuilder:toString	()Ljava/lang/String;
      //   190: invokespecial 177	com/google/android/apps/plus/iu/Uploader$UploadException:<init>	(Ljava/lang/String;)V
      //   193: athrow
      //   194: return
      //
      // Exception table:
      //   from	to	target	type
      //   19	41	83	java/lang/NumberFormatException
      //   41	63	116	java/lang/NumberFormatException
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.iu.GDataUploader
 * JD-Core Version:    0.6.2
 */