package com.google.android.apps.plus.hangout.crash;

import android.app.Activity;
import android.os.AsyncTask;
import android.os.Build;
import android.os.Build.VERSION;
import com.google.android.apps.plus.hangout.Utils;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public final class CrashReport
{
  private static final List<String> LOG_TAGS = Arrays.asList(new String[] { "GoogleMeeting", "gcomm_native", "libjingle", "DEBUG" });
  private String crashProcessingError = "uninitialized non-null value";
  private final boolean isTestCrash = true;
  private final Map<String, String> params = new HashMap();
  private String reportText;
  private String signature = "";

  public CrashReport(boolean paramBoolean)
  {
  }

  private static void appendNonHangoutLog(StringBuilder paramStringBuilder, String paramString)
  {
    paramStringBuilder.append(paramString);
    paramStringBuilder.append("\n");
  }

  public static String computeJavaCrashSignature(Throwable paramThrowable)
  {
    int[] arrayOfInt = new int[10];
    int i = -1;
    StackTraceElement[] arrayOfStackTraceElement = paramThrowable.getStackTrace();
    int j = Math.min(arrayOfStackTraceElement.length, 10);
    for (int k = 0; k < j; k++)
    {
      int i1 = i + 1;
      int i2 = i1 % arrayOfInt.length;
      arrayOfInt[i2] ^= arrayOfStackTraceElement[k].getClassName().hashCode();
      i = i1 + 1;
      int i3 = i % arrayOfInt.length;
      arrayOfInt[i3] ^= arrayOfStackTraceElement[k].getMethodName().hashCode();
    }
    StringBuilder localStringBuilder = new StringBuilder();
    int m = arrayOfInt.length;
    for (int n = 0; n < m; n++)
      localStringBuilder.append(Integer.toHexString(arrayOfInt[n]));
    return localStringBuilder.toString();
  }

  private boolean getSystemLogs()
  {
    boolean bool = true;
    try
    {
      Process localProcess = Runtime.getRuntime().exec(new String[] { "logcat", "-d", "-v", "threadtime" });
      StringBuilder localStringBuilder = new StringBuilder();
      processLogs(localStringBuilder, localProcess.getInputStream());
      this.reportText = localStringBuilder.toString();
      this.crashProcessingError = "Logs successfully processed";
      localProcess.destroy();
      return bool;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        com.google.android.apps.plus.hangout.Log.error(localIOException.toString());
        Object[] arrayOfObject = new Object[2];
        arrayOfObject[0] = localIOException.toString();
        arrayOfObject[bool] = android.util.Log.getStackTraceString(localIOException);
        this.crashProcessingError = String.format("Error getting system logs: %s\n%s", arrayOfObject);
        bool = false;
      }
    }
  }

  private void processLogs(StringBuilder paramStringBuilder, InputStream paramInputStream)
    throws IOException
  {
    BufferedReader localBufferedReader = new BufferedReader(new InputStreamReader(paramInputStream));
    int i = 0;
    Pattern localPattern1 = Pattern.compile("[\\p{Digit}-]+ \\p{Space}+[\\p{Digit}\\.:]+ \\p{Space}+[\\p{Digit}]+ \\p{Space}+[\\p{Digit}]+ \\p{Space}+\\p{Upper} \\p{Space}+([^:]+):((.*))", 4);
    Pattern localPattern2 = Pattern.compile("\\p{Space}+\\# [\\p{Digit}]+ \\p{Space}+pc \\p{Space}+(([\\p{XDigit}]{8})) \\p{Space}+[\\p{Alnum}/\\._-}]*libgcomm_jni\\.so", 4);
    while (true)
    {
      String str1 = localBufferedReader.readLine();
      if (str1 == null)
        break;
      Matcher localMatcher1 = localPattern1.matcher(str1);
      if (!localMatcher1.matches())
      {
        appendNonHangoutLog(paramStringBuilder, str1);
      }
      else
      {
        String str2 = localMatcher1.group(1).trim();
        String str3 = localMatcher1.group(2);
        if (LOG_TAGS.indexOf(str2) < 0)
        {
          appendNonHangoutLog(paramStringBuilder, str1);
        }
        else
        {
          paramStringBuilder.append(str1);
          paramStringBuilder.append("\n");
          if (str2.equals("DEBUG"))
          {
            if (str3.contains("*** *** *** *** ***"))
            {
              i = 1;
              this.signature = "";
            }
            if (i != 0)
            {
              Matcher localMatcher2 = localPattern2.matcher(str3);
              if ((localMatcher2.find()) && (this.signature.length() < 80))
              {
                if (this.signature.length() > 0)
                  this.signature += ",";
                this.signature += localMatcher2.group(1);
              }
            }
          }
        }
      }
    }
  }

  public final boolean generateReport(String paramString)
  {
    getSystemLogs();
    if (paramString != null)
      this.signature = paramString;
    this.params.put("prod", "Google_Plus_Android");
    Map localMap = this.params;
    String str;
    if (this.isTestCrash)
    {
      str = "Test-" + Utils.getVersion();
      localMap.put("ver", str);
      this.params.put("sig", this.signature);
      this.params.put("sig2", this.signature);
      this.params.put("should_process", "F");
      this.params.put("build_board", Build.BOARD);
      this.params.put("build_brand", Build.BRAND);
      this.params.put("build_device", Build.DEVICE);
      this.params.put("build_id", Build.ID);
      this.params.put("build_manufacturer", Build.MANUFACTURER);
      this.params.put("build_model", Build.MODEL);
      this.params.put("build_product", Build.PRODUCT);
      this.params.put("build_type", Build.TYPE);
      this.params.put("version_codename", Build.VERSION.CODENAME);
      this.params.put("version_incremental", Build.VERSION.INCREMENTAL);
      this.params.put("version_release", Build.VERSION.RELEASE);
      this.params.put("version_sdk_int", Integer.toString(Build.VERSION.SDK_INT));
      if (this.isTestCrash)
        this.params.put("testing", "true");
      if (this.reportText == null)
        break label361;
    }
    label361: for (boolean bool = true; ; bool = false)
    {
      return bool;
      str = Utils.getVersion();
      break;
    }
  }

  public final void send(final Activity paramActivity, boolean paramBoolean)
  {
    new AsyncTask()
    {
    }
    .execute(new Void[0]);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.hangout.crash.CrashReport
 * JD-Core Version:    0.6.2
 */