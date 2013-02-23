package com.google.android.apps.plus.hangout;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.media.MediaScannerConnection;
import android.media.MediaScannerConnection.OnScanCompletedListener;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.util.Log;
import com.google.android.apps.plus.R.raw;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.util.EsLog;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class HangoutNotifications
{
  private static File copyResourceToFile(int paramInt, Context paramContext)
  {
    File localFile1 = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_NOTIFICATIONS);
    File localFile2 = new File(localFile1, "hangout_dingtone.m4a");
    try
    {
      if (localFile2.exists())
      {
        if (EsLog.isLoggable("ExternalStorageUtils", 4))
          Log.i("ExternalStorageUtils", "Notification sound already present");
      }
      else
      {
        localFile1.mkdirs();
        if (EsLog.isLoggable("ExternalStorageUtils", 3))
          Log.d("ExternalStorageUtils", "Copy notification to " + localFile2.toString());
        InputStream localInputStream = paramContext.getResources().openRawResource(paramInt);
        byte[] arrayOfByte = new byte[localInputStream.available()];
        localInputStream.read(arrayOfByte);
        localInputStream.close();
        FileOutputStream localFileOutputStream = new FileOutputStream(localFile2);
        localFileOutputStream.write(arrayOfByte);
        localFileOutputStream.flush();
        localFileOutputStream.close();
      }
    }
    catch (IOException localIOException)
    {
      Log.w("ExternalStorageUtils", "Error writing to " + localFile2.toString(), localIOException);
    }
    return localFile2;
  }

  public static Uri getDingtone(Context paramContext)
  {
    String str1 = paramContext.getString(R.string.hangout_dingtone_setting_key);
    String str2 = PreferenceManager.getDefaultSharedPreferences(paramContext).getString(str1, null);
    Uri localUri;
    if (str2 != null)
    {
      if (EsLog.isLoggable("ExternalStorageUtils", 4))
        Log.i("ExternalStorageUtils", "Hangout dingtone; uri: " + str2);
      localUri = Uri.parse(str2);
    }
    while (true)
    {
      return localUri;
      boolean bool = EsLog.isLoggable("ExternalStorageUtils", 4);
      localUri = null;
      if (bool)
      {
        Log.i("ExternalStorageUtils", "Hangout dingtone not set");
        localUri = null;
      }
    }
  }

  public static void registerHangoutSounds(Context paramContext)
  {
    File localFile = copyResourceToFile(R.raw.hangout_dingtone, paramContext);
    String[] arrayOfString = new String[1];
    arrayOfString[0] = localFile.toString();
    MediaScannerConnection.scanFile(paramContext, arrayOfString, null, new MediaScannerConnection.OnScanCompletedListener()
    {
      public final void onScanCompleted(String paramAnonymousString, Uri paramAnonymousUri)
      {
        if (EsLog.isLoggable("ExternalStorageUtils", 4))
          Log.i("ExternalStorageUtils", "Scan complete; uri: " + paramAnonymousUri);
        if (!HangoutNotifications.access$000(this.val$context))
          HangoutNotifications.access$100(this.val$context, paramAnonymousUri);
      }
    });
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.HangoutNotifications
 * JD-Core Version:    0.6.2
 */