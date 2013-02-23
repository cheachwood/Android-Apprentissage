package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.webkit.MimeTypeMap;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.phone.ImageProxyUtil;
import com.google.android.apps.plus.util.EsLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.apache.http.Header;

public final class SavePhotoOperation extends DownloadPhotoOperation
{
  private static final Pattern CONTENT_DISPOSITION_PATTERN = Pattern.compile("attachment;\\s*filename\\s*=\\s*(\"?)([^\"]*)\\1\\s*$", 2);
  private static final File SAVE_TO_DIRECTORY = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS);
  private String mContentType;
  private String mSaveToName;

  public SavePhotoOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, String paramString, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, "GET", paramString, paramEsAccount, null, paramIntent, paramOperationListener);
  }

  private static String parseContentDisposition(String paramString)
  {
    try
    {
      Matcher localMatcher = CONTENT_DISPOSITION_PATTERN.matcher(paramString);
      if (localMatcher.find())
      {
        String str2 = localMatcher.group(2);
        str1 = str2;
        return str1;
      }
    }
    catch (IllegalStateException localIllegalStateException)
    {
      while (true)
        String str1 = null;
    }
  }

  public final String getContentType()
  {
    return this.mContentType;
  }

  public final File getSaveToFile()
  {
    if (TextUtils.isEmpty(this.mSaveToName));
    for (File localFile = null; ; localFile = new File(SAVE_TO_DIRECTORY, this.mSaveToName))
      return localFile;
  }

  public final void onHttpReadFromStream(InputStream paramInputStream, String paramString, int paramInt, Header[] paramArrayOfHeader)
    throws IOException
  {
    this.mContentType = paramString;
    String str1 = getUrl();
    int i = 0;
    if (i < paramArrayOfHeader.length)
      if (!"Content-Disposition".equals(paramArrayOfHeader[i].getName()));
    for (String str2 = paramArrayOfHeader[i].getValue(); ; str2 = null)
    {
      String str7;
      label99: int k;
      String str8;
      if (str2 != null)
        if (str2 != null)
        {
          str7 = parseContentDisposition(str2);
          if (str7 != null)
          {
            int i2 = 1 + str7.lastIndexOf('/');
            if (i2 > 0)
              str7 = str7.substring(i2);
          }
          if (TextUtils.isEmpty(str7))
          {
            String str10 = Uri.decode(str1);
            if (str10 != null)
            {
              int n = str10.indexOf('?');
              if (n > 0)
                str10 = str10.substring(0, n);
              if (!str10.endsWith("/"))
              {
                int i1 = 1 + str10.lastIndexOf('/');
                if (i1 > 0)
                  str7 = str10.substring(i1);
              }
            }
          }
          if (TextUtils.isEmpty(str7))
            str7 = "downloadfile";
          k = str7.indexOf('.');
          if (k < 0)
          {
            if (paramString == null)
              break label819;
            str8 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
            if (str8 != null)
              str8 = "." + str8;
            label241: if (str8 == null)
            {
              if ((paramString == null) || (!paramString.toLowerCase().startsWith("text/")))
                break label556;
              if (!paramString.equalsIgnoreCase("text/html"))
                break label549;
              str8 = ".html";
            }
          }
        }
      label275: for (String str3 = str7 + str8; ; str3 = null)
      {
        String str5;
        label377: File localFile;
        if ((TextUtils.isEmpty(str3)) || (ImageProxyUtil.isProxyHostedUrl(str1)))
        {
          Date localDate = new Date(System.currentTimeMillis());
          SimpleDateFormat localSimpleDateFormat = new SimpleDateFormat(this.mContext.getString(R.string.download_file_name_format));
          String str4 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
          if (str4 != null)
          {
            str5 = "." + str4;
            str3 = localSimpleDateFormat.format(localDate) + str5;
          }
        }
        else
        {
          if (new File(SAVE_TO_DIRECTORY, str3).exists())
            break label674;
          label422: this.mSaveToName = str3;
          boolean bool1 = TextUtils.isEmpty(this.mSaveToName);
          localFile = null;
          if (!bool1)
          {
            if (EsLog.isLoggable("HttpTransaction", 3))
              Log.d("HttpTransaction", "Saving image to local: " + this.mSaveToName);
            if (!SAVE_TO_DIRECTORY.exists())
            {
              boolean bool2 = SAVE_TO_DIRECTORY.mkdirs();
              localFile = null;
              if (!bool2);
            }
            else
            {
              localFile = new File(SAVE_TO_DIRECTORY, this.mSaveToName);
              setOutputStream(new FileOutputStream(localFile));
            }
          }
        }
        while (true)
        {
          try
          {
            super.onHttpReadFromStream(paramInputStream, paramString, paramInt, paramArrayOfHeader);
            return;
            i++;
            break;
            label549: str8 = ".txt";
            break label275;
            label556: str8 = ".bin";
            break label275;
            if (paramString != null)
            {
              int m = str7.lastIndexOf('.');
              String str9 = MimeTypeMap.getSingleton().getMimeTypeFromExtension(str7.substring(m + 1));
              if ((str9 != null) && (!str9.equalsIgnoreCase(paramString)))
              {
                str8 = MimeTypeMap.getSingleton().getExtensionFromMimeType(paramString);
                if (str8 != null)
                  str8 = "." + str8;
                if (str8 == null)
                  str8 = str7.substring(k);
                str7 = str7.substring(0, k);
                break label275;
                str5 = "";
                break label377;
                label674: String[] arrayOfString = TextUtils.split(str3, "\\.");
                if (arrayOfString.length != 0)
                {
                  arrayOfString[0] = (arrayOfString[0] + " (%d)");
                  String str6 = TextUtils.join(".", arrayOfString);
                  int j = 1;
                  if (j <= 99)
                  {
                    Object[] arrayOfObject = new Object[1];
                    arrayOfObject[0] = Integer.valueOf(j);
                    str3 = String.format(str6, arrayOfObject);
                    if (!new File(SAVE_TO_DIRECTORY, str3).exists())
                      break label422;
                    j++;
                    continue;
                  }
                }
                str3 = null;
              }
            }
          }
          catch (IOException localIOException)
          {
            if ((localFile != null) && (localFile.exists()))
              localFile.delete();
            throw localIOException;
          }
          str8 = null;
        }
        label819: str8 = null;
        break label241;
        str7 = null;
        break label99;
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SavePhotoOperation
 * JD-Core Version:    0.6.2
 */