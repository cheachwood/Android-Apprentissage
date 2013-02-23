package com.google.android.apps.plus.hangout.crash;

import com.google.android.apps.plus.hangout.Log;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

public final class CrashSender
{
  public static boolean sendReport$55085047(Map<String, String> paramMap, String paramString, byte[] paramArrayOfByte)
  {
    String str1 = "----------" + Long.toString(()(1234567890123.0D * Math.random())) + System.currentTimeMillis();
    try
    {
      HttpURLConnection localHttpURLConnection = (HttpURLConnection)new URL("https://clients2.google.com/cr/report").openConnection();
      localHttpURLConnection.setDoOutput(true);
      localHttpURLConnection.setRequestMethod("POST");
      localHttpURLConnection.setRequestProperty("Content-Type", "multipart/form-data; boundary=" + str1);
      writePostContent(localHttpURLConnection.getOutputStream(), str1, paramMap, paramString, paramArrayOfByte);
      int i = localHttpURLConnection.getResponseCode();
      Object[] arrayOfObject = new Object[2];
      arrayOfObject[0] = paramMap.get("sig");
      arrayOfObject[1] = Integer.valueOf(i);
      Log.info("Sent crash report with signature %s, response %d", arrayOfObject);
      if (i == 200)
      {
        String str2 = new BufferedReader(new InputStreamReader(localHttpURLConnection.getInputStream())).readLine();
        Log.info("Report id: " + str2);
      }
      for (bool = true; ; bool = false)
        return bool;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        Log.error(localIOException.toString());
        boolean bool = false;
      }
    }
  }

  private static void writePostContent(OutputStream paramOutputStream, String paramString1, Map<String, String> paramMap, String paramString2, byte[] paramArrayOfByte)
    throws IOException
  {
    Iterator localIterator = paramMap.entrySet().iterator();
    while (localIterator.hasNext())
    {
      Map.Entry localEntry = (Map.Entry)localIterator.next();
      paramOutputStream.write(("--" + paramString1 + "\r\nContent-Disposition: form-data; name=\"" + (String)localEntry.getKey() + "\"\r\n" + "\r\n" + (String)localEntry.getValue() + "\r\n").getBytes());
    }
    if (paramArrayOfByte != null)
    {
      paramOutputStream.write(("--" + paramString1 + "\r\nContent-Disposition: form-data; name=\"" + paramString2 + "\"; filename=\"" + paramString2 + "\"\r\n" + "Content-Type: application/octet-stream\r\n" + "\r\n").getBytes());
      paramOutputStream.write(paramArrayOfByte);
      paramOutputStream.write("\r\n".getBytes());
    }
    paramOutputStream.write(("--" + paramString1 + "--\r\n").getBytes());
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.crash.CrashSender
 * JD-Core Version:    0.6.2
 */