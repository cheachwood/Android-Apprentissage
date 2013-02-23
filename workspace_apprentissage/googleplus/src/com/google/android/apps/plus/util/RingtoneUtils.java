package com.google.android.apps.plus.util;

import android.content.ContentResolver;
import android.content.ContentValues;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.res.Resources;
import android.database.Cursor;
import android.net.Uri;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.provider.MediaStore.Audio.Media;
import android.util.Log;
import com.google.android.apps.plus.R.raw;
import com.google.android.apps.plus.R.string;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

public final class RingtoneUtils
{
  private static String getHangoutRingtoneFileName(Context paramContext)
  {
    return paramContext.getResources().getResourceEntryName(R.raw.hangout_ringtone) + ".ogg";
  }

  private static String getHangoutRingtonePath(Context paramContext)
  {
    return Environment.getExternalStorageDirectory().toString() + File.separator + paramContext.getString(R.string.hangout_ringtone_directory);
  }

  public static void registerHangoutRingtoneIfNecessary(Context paramContext)
  {
    int i;
    String str1;
    String str2;
    String str3;
    try
    {
      File localFile1 = new File(getHangoutRingtonePath(paramContext), getHangoutRingtoneFileName(paramContext));
      if (!localFile1.exists())
      {
        i = 1;
        break label608;
      }
      Uri localUri1 = MediaStore.Audio.Media.getContentUriForPath(localFile1.getAbsolutePath());
      Cursor localCursor = paramContext.getContentResolver().query(localUri1, new String[] { "_data" }, "_data=\"" + localFile1.getAbsolutePath() + "\"", null, null);
      if (localCursor == null)
        break label614;
      if (localCursor.getCount() != 0)
        break label620;
      break label614;
      str1 = getHangoutRingtonePath(paramContext);
      str2 = getHangoutRingtoneFileName(paramContext);
      str3 = paramContext.getString(R.string.hangout_ringtone_name);
      File localFile2 = new File(str1);
      localFile2.mkdirs();
      if (!localFile2.exists())
      {
        if (!EsLog.isLoggable("RingtoneUtils", 6))
          break label613;
        Log.e("RingtoneUtils", String.format("Could not create the directory %s", new Object[] { str1 }));
      }
    }
    catch (Throwable localThrowable)
    {
      Log.e("RingtoneUtils", "Could not register the Hangout ringtone", localThrowable);
    }
    InputStream localInputStream = paramContext.getResources().openRawResource(R.raw.hangout_ringtone);
    File localFile3;
    try
    {
      byte[] arrayOfByte = new byte[localInputStream.available()];
      localInputStream.read(arrayOfByte);
      localInputStream.close();
      FileOutputStream localFileOutputStream = new FileOutputStream(str1 + File.separator + str2);
      localFileOutputStream.write(arrayOfByte);
      localFileOutputStream.close();
      localFile3 = new File(str1, str2);
      if (localFile3.exists())
        break label359;
      if (!EsLog.isLoggable("RingtoneUtils", 6))
        break label613;
      Log.e("RingtoneUtils", String.format("Could not create the file %s/%s for the Hangout ringtone", new Object[] { str1, str2 }));
    }
    catch (IOException localIOException)
    {
      if (!EsLog.isLoggable("RingtoneUtils", 6))
        break label613;
    }
    Log.e("RingtoneUtils", "Could not create a file for the Hangout ringtone", localIOException);
    break label613;
    label359: ContentValues localContentValues = new ContentValues();
    localContentValues.put("_data", localFile3.getAbsolutePath());
    localContentValues.put("title", str3);
    localContentValues.put("mime_type", "audio/ogg");
    localContentValues.put("_size", Long.valueOf(localFile3.length()));
    localContentValues.put("artist", Integer.valueOf(R.string.app_name));
    localContentValues.put("is_ringtone", Boolean.valueOf(true));
    localContentValues.put("is_notification", Boolean.valueOf(true));
    localContentValues.put("is_alarm", Boolean.valueOf(true));
    localContentValues.put("is_music", Boolean.valueOf(false));
    Uri localUri2 = MediaStore.Audio.Media.getContentUriForPath(localFile3.getAbsolutePath());
    ContentResolver localContentResolver = paramContext.getContentResolver();
    localContentResolver.delete(localUri2, "_data=\"" + localFile3.getAbsolutePath() + "\"", null);
    localContentResolver.delete(localUri2, "title=\"" + str3 + "\"", null);
    Uri localUri3 = localContentResolver.insert(localUri2, localContentValues);
    String str4 = paramContext.getString(R.string.hangout_ringtone_setting_key);
    SharedPreferences.Editor localEditor = PreferenceManager.getDefaultSharedPreferences(paramContext).edit();
    localEditor.putString(str4, localUri3.toString());
    localEditor.commit();
    label608: 
    while (i == 0)
    {
      label613: return;
      label614: i = 1;
      continue;
      label620: i = 0;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.util.RingtoneUtils
 * JD-Core Version:    0.6.2
 */