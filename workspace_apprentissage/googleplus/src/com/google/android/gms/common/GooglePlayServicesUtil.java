package com.google.android.gms.common;

import [[B;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.net.Uri;
import android.os.Build;
import android.os.Build.VERSION;
import android.util.Base64;
import android.util.Log;
import com.google.android.gms.R.string;
import com.google.android.gms.internal.aw;
import com.google.android.gms.internal.t;

public final class GooglePlayServicesUtil
{
  private static final String TAG = GooglePlayServicesUtil.class.getSimpleName();
  private static final byte[][] aE;
  private static final byte[][] aF;
  private static final byte[][] aG;
  private static final byte[][] aH;
  private static final byte[][] aI = arrayOfByte5;
  static boolean aJ = false;
  static boolean aK = false;
  static boolean aL = false;

  static
  {
    byte[][] arrayOfByte1 = new byte[2][];
    arrayOfByte1[0] = Base64.decode("MIIEQzCCAyugAwIBAgIJAMLgh0ZkSjCNMA0GCSqGSIb3DQEBBAUAMHQxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtHb29nbGUgSW5jLjEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDAeFw0wODA4MjEyMzEzMzRaFw0zNjAxMDcyMzEzMzRaMHQxCzAJBgNVBAYTAlVTMRMwEQYDVQQIEwpDYWxpZm9ybmlhMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtHb29nbGUgSW5jLjEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDCCASAwDQYJKoZIhvcNAQEBBQADggENADCCAQgCggEBAKtWLgDYO6IIrgqWbxJOKdoR8qtW0I9Y4sypEwPpt1TTcvZApxsdyxMJZ2JORland2qSGT2y5b+3JKkedxiLDmpHpDsz2WCbdxgxRczfey5YZnTJ4VZbH0xqWVW/8lGmPav5xVwnIiJS6HXk+BVKZF+JcWjAsb/GEuq/eFdpuzSqeYTcfi6idkyugwfYwXFU1+5fZKUaRKYCwkkFQVfcAs1fXA5V+++FGfvjJ/CxURaSxaBvGdGDhfXE28LWuT9ozCl5xw4Yq5OGazvV24mZVSoOO0yZ31j7kYvtwYK6NeADwbSxDdJEqO4k//0zOHKrUiGYXtqw/A0LFFtqoZKFjnkCAQOjgdkwgdYwHQYDVR0OBBYEFMd9jMIhF1Ylmn/Tgt9r45jk14alMIGmBgNVHSMEgZ4wgZuAFMd9jMIhF1Ylmn/Tgt9r45jk14aloXikdjB0MQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEUMBIGA1UEChMLR29vZ2xlIEluYy4xEDAOBgNVBAsTB0FuZHJvaWQxEDAOBgNVBAMTB0FuZHJvaWSCCQDC4IdGZEowjTAMBgNVHRMEBTADAQH/MA0GCSqGSIb3DQEBBAUAA4IBAQBt0lLO74UwLDYKqs6Tm8/yzKkEu116FmH4rkaymUIE0P9KaMftGlMexFlaYjzmB2OxZyl6euNXEsQH8gjwyxCUKRJNexBiGcCEyj6z+a1fuHHvkiaai+KL8W1EyNmgjmyy8AW7P+LLlkR+ho5zEHatRbM/YAnqGcFh5iZBqpknHf1SKMXFh4dd239FJ1jWYfbMDMy3NS5CTMQ2XFI1MvcyUTdZPErjQfTbQe3aDQsQcafEQPD+nqActifKZ0Np0IS9L9kR/wbNvyz6ENwPiTrjV2KRkEjH78ZMcUQXg0L3BYHJ3lc69Vs5Ddf9uUGGMYldX3WfMBEmh/9iFBDAaTCK", 0);
    arrayOfByte1[1] = Base64.decode("MIIEqDCCA5CgAwIBAgIJANWFuGx90071MA0GCSqGSIb3DQEBBAUAMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTAeFw0wODA0MTUyMzM2NTZaFw0zNTA5MDEyMzM2NTZaMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTCCASAwDQYJKoZIhvcNAQEBBQADggENADCCAQgCggEBANbOLggKv+IxTdGNs8/TGFy0PTP6DHThvbbR24kT9ixcOd9W+EaBPWW+wPPKQmsHxajtWjmQwWfna8mZuSeJS48LIgAZlKkpFeVyxW0qMBujb8X8ETrWy550NaFtI6t9+u7hZeTfHwqNvacKhp1RbE6dBRGWynwMVX8XW8N1+UjFaq6GCJukT4qmpN2afb8sCjUigq0GuMwYXrFVee74bQgLHWGJwPmvmLHC69EH6kWr22ijx4OKXlSIx2xT1AsSHee70w5iDBiK4aph27yH3TxkXy9V89TDdexAcKk/cVHYNnDBapcavl7y0RiQ4biu8ymM8Ga/nmzhRKya6G0cGw8CAQOjgfwwgfkwHQYDVR0OBBYEFI0cxb6VTEM8YYY6FbBMvAPyT+CyMIHJBgNVHSMEgcEwgb6AFI0cxb6VTEM8YYY6FbBMvAPyT+CyoYGapIGXMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbYIJANWFuGx90071MAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEEBQADggEBABnTDPEF+3iSP0wNfdIjIz1AlnrPzgAIHVvXxunW7SBrDhEglQZBbKJEk5kT0mtKoOD1JMrSu1xuTKEBahWRbqHsXclaXjoBADb0kkjVEJu/Lh5hgYZnOjvlba8Ld7HCKePCVePoTJBdI4fvugnL8TsgK05aIskyY0hKI9L8KfqfGTl1lzOv2KoWD0KWwtAWPoGChZxmQ+nBli+gwYMzM1vAkP+aayLe0a1EQimlOalO762r0GXO0ks+UeXde2Z4e+8S/pf7pITEI/tP+MxJTALw9QUWEv9lKTk+jkbqxbsh8nfBUapfKqYn0eidpwq2AzVp3juYl7//fKnaPhJD9gs=", 0);
    aE = arrayOfByte1;
    byte[][] arrayOfByte2 = new byte[2][];
    arrayOfByte2[0] = Base64.decode("MIICUjCCAbsCBEk0mH4wDQYJKoZIhvcNAQEEBQAwcDELMAkGA1UEBhMCVVMxCzAJBgNVBAgTAkNBMRYwFAYDVQQHEw1Nb3VudGFpbiBWaWV3MRQwEgYDVQQKEwtHb29nbGUsIEluYzEUMBIGA1UECxMLR29vZ2xlLCBJbmMxEDAOBgNVBAMTB1Vua25vd24wHhcNMDgxMjAyMDIwNzU4WhcNMzYwNDE5MDIwNzU4WjBwMQswCQYDVQQGEwJVUzELMAkGA1UECBMCQ0ExFjAUBgNVBAcTDU1vdW50YWluIFZpZXcxFDASBgNVBAoTC0dvb2dsZSwgSW5jMRQwEgYDVQQLEwtHb29nbGUsIEluYzEQMA4GA1UEAxMHVW5rbm93bjCBnzANBgkqhkiG9w0BAQEFAAOBjQAwgYkCgYEAn0gDGZD5sUcmOE4EU9GPjAu/jcd7JQSksSB8TGxEurwArcZhD6a2qy2oDjPy7vFrJqP2uFua+sqQn/u+s/TJT36BIqeY4OunXO090in6c2X0FRZBWqnBYX3Vg84Zuuigu9iF/BeptL0mQIBRIarbk3fetAATOBQYiC7FIoL8WA0CAwEAATANBgkqhkiG9w0BAQQFAAOBgQBAhmae1jHaQ4Td0GHSJuBzuYzEuZ34teS+njy+l1Aeg98cb6lZwM5gXE/SrG0chM7eIEdsurGb6PIgOv93F61lLY/MiQcI0SFtqERXWSZJ4OnTxLtM9Y2hnbHU/EG8uVhPZOZfQQ0FKf1baIOMFB0Km9HbEZHLKg33kOoMsS2zpA==", 0);
    arrayOfByte2[1] = Base64.decode("MIIEqDCCA5CgAwIBAgIJAIR+T/LWtd6OMA0GCSqGSIb3DQEBBQUAMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTAeFw0xMDAxMjAwMTAxMzVaFw0zNzA2MDcwMTAxMzVaMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbTCCASAwDQYJKoZIhvcNAQEBBQADggENADCCAQgCggEBANgocXw20RcP1E0Kew8HESboW7/fM7A0YABalMz7ZaXboLJD32Cxkb+dBt8dilwKM+LRY/UT3x0iU0HqPDN5IuhcAuw0ztlMuAcjpiP/S6/7tOXv5nc7PqK+uLyyAmfP54VRH4Mu+YerdZT+HinPvE0IOh8SUgB3c5byFltpewCjoME6zDCKk/IhY8FunD1KshSfNkxFwEMUMnA58doJYJPxs/wYtlYQlcYiX8cQK5h8bxOkXSTj4MVOhZ1n41tnCCcT0tbwV900V9GfxP6N3eyMOk8/lyMFGacKKDY0rDWBo0q9oX2EWgoJhfv4BgsDaid4YIFj+gw3uefyoQ52vHcCAQOjgfwwgfkwHQYDVR0OBBYEFLXH+RJveA06+8plc3M/9SJrmxc3MIHJBgNVHSMEgcEwgb6AFLXH+RJveA06+8plc3M/9SJrmxc3oYGapIGXMIGUMQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEQMA4GA1UEChMHQW5kcm9pZDEQMA4GA1UECxMHQW5kcm9pZDEQMA4GA1UEAxMHQW5kcm9pZDEiMCAGCSqGSIb3DQEJARYTYW5kcm9pZEBhbmRyb2lkLmNvbYIJAIR+T/LWtd6OMAwGA1UdEwQFMAMBAf8wDQYJKoZIhvcNAQEFBQADggEBAEw+p2V9Jua71xEMjxnfH42hCX0zhg9p3r/K20ajfoflsw+7NHscdVW8uzyZVBSARpZfnHkqAtDb5aZHYbN5R6tr/7C6xqLBoM34Yvh3qWcN/W8GLkBuzhgGDGBJjfw2nycRcZjlb8uhUuYFjc6UzlkfxPSpmCszutgZbXdvVbfQGs8x3dcM7LeJeHYGZRD5SaVSSjExs8tlQc+LNUIOvMRSJVmWP0JmaQVyZmJPs5jP21IXiB0RHG4DRhb4USEY0KKmnRPXkvDNEdvVjiODWlSlSsJR59IsRGo/7hQSEOlER0tAYwe7JoQrT2vTVYIcc5ZR/6JgWwXiJJXXFdh6kfY=", 0);
    aF = arrayOfByte2;
    byte[][] arrayOfByte3 = new byte[1][];
    arrayOfByte3[0] = Base64.decode("MIICpzCCAmWgAwIBAgIEUAV8QjALBgcqhkjOOAQDBQAwNzELMAkGA1UEBhMCVVMxEDAOBgNVBAoTB0FuZHJvaWQxFjAUBgNVBAMTDUFuZHJvaWQgRGVidWcwHhcNMTIwNzE3MTQ1MjUwWhcNMjIwNzE1MTQ1MjUwWjA3MQswCQYDVQQGEwJVUzEQMA4GA1UEChMHQW5kcm9pZDEWMBQGA1UEAxMNQW5kcm9pZCBEZWJ1ZzCCAbcwggEsBgcqhkjOOAQBMIIBHwKBgQD9f1OBHXUSKVLfSpwu7OTn9hG3UjzvRADDHj+AtlEmaUVdQCJR+1k9jVj6v8X1ujD2y5tVbNeBO4AdNG/yZmC3a5lQpaSfn+gEexAiwk+7qdf+t8Yb+DtX58aophUPBPuD9tPFHsMCNVQTWhaRMvZ1864rYdcq7/IiAxmd0UgBxwIVAJdgUI8VIwvMspK5gqLrhAvwWBz1AoGBAPfhoIXWmz3ey7yrXDa4V7l5lK+7+jrqgvlXTAs9B4JnUVlXjrrUWU/mcQcQgYC0SRZxI+hMKBYTt88JMozIpuE8FnqLVHyNKOCjrh4rs6Z1kW6jfwv6ITVi8ftiegEkO8yk8b6oUZCJqIPf4VrlnwaSi2ZegHtVJWQBTDv+z0kqA4GEAAKBgGrRG9fVZtJ69DnALkForP1FtL6FvJmMe5uOHHdUaT+MDUKKpPzhEISBOEJPpozRMFJO7/bxNzhjgi+mNymL/k1GoLhmZe7wQRc5AQNbHIBqoxgYDTA6qMyeWSPgam+r+nVoPEU7sgd3fPL958+xmxQwOBSqHfe0PVsiK1cGtIuUMAsGByqGSM44BAMFAAMvADAsAhQJ0tGwRwIptb7SkCZh0RLycMXmHQIUZ1ACBqeAULp4rscXTxYEf4Tqovc=", 0);
    aG = arrayOfByte3;
    byte[][][] arrayOfByte = new byte[3][][];
    arrayOfByte[0] = aE;
    arrayOfByte[1] = aF;
    arrayOfByte[2] = aG;
    int i = arrayOfByte.length;
    int j = 0;
    int k = 0;
    while (j < i)
    {
      k += arrayOfByte[j].length;
      j++;
    }
    byte[][] arrayOfByte4 = new byte[k][];
    int m = arrayOfByte.length;
    int n = 0;
    int i2;
    for (int i1 = 0; n < m; i1 = i2)
    {
      [[B local[[B = arrayOfByte[n];
      i2 = i1;
      int i3 = 0;
      while (i3 < local[[B.length)
      {
        int i4 = i2 + 1;
        arrayOfByte4[i2] = local[[B[i3];
        i3++;
        i2 = i4;
      }
      n++;
    }
    aH = arrayOfByte4;
    byte[][] arrayOfByte5 = new byte[1][];
    arrayOfByte5[0] = Base64.decode("MIICXzCCAcigAwIBAgIESxmxnTANBgkqhkiG9w0BAQUFADB0MQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEVMBMGA1UEChMMR29vZ2xlLCBJbmMuMRAwDgYDVQQLEwdVbmtub3duMQ8wDQYDVQQDEwZCYXphYXIwHhcNMDkxMjA1MDEwNDI5WhcNMzcwNDIyMDEwNDI5WjB0MQswCQYDVQQGEwJVUzETMBEGA1UECBMKQ2FsaWZvcm5pYTEWMBQGA1UEBxMNTW91bnRhaW4gVmlldzEVMBMGA1UEChMMR29vZ2xlLCBJbmMuMRAwDgYDVQQLEwdVbmtub3duMQ8wDQYDVQQDEwZCYXphYXIwgZ8wDQYJKoZIhvcNAQEBBQADgY0AMIGJAoGBAKkIiN6W4zU0dwndSyUeeimoRzdrLly6W1vVBD6DiAECmBkUlBP6M6rlRDsDU0rOSq1vUJcSSdmOdqOafkzM4dcbp74+dWdNtfEHWphzcAFGSKfOcDwtx4g0iQWSEq+cbFsoq9VPg2QRyDGin1APKALRbObRhW+GcKr8omVBg3s5AgMBAAEwDQYJKoZIhvcNAQEFBQADgYEASYTG80FHASNiOidP6eE3PXUxzA386adq5n/7cFtATL0bwRaMqxi7EcN4lb+082zBTOwdLMVRag7O1AdOtWiCiVBkAK/43MjvVAQSAv3v8f2C4PMjEHL9zN5KNovgxsP5uLOqDWg8Or/amre7iDLpvl42GbqS3TrMA2qttaYZr1A=", 0);
  }

  public static Intent a(Context paramContext, int paramInt1, int paramInt2)
  {
    Intent localIntent = null;
    switch (paramInt1)
    {
    default:
    case 1:
    case 2:
    case 3:
    }
    while (true)
    {
      return localIntent;
      if (d(paramInt2))
      {
        if (e(paramContext))
        {
          Uri localUri2 = Uri.parse("bazaar://search?q=pname:" + "com.google.android.gms");
          localIntent = new Intent("android.intent.action.VIEW");
          localIntent.setData(localUri2);
          localIntent.setFlags(524288);
        }
        else
        {
          localIntent = t.d("com.google.android.apps.bazaar");
        }
      }
      else
      {
        localIntent = t.d("com.google.android.gms");
        continue;
        Uri localUri1 = Uri.fromParts("package", "com.google.android.gms", null);
        localIntent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
        localIntent.setData(localUri1);
      }
    }
  }

  public static String a(Context paramContext, int paramInt)
  {
    Resources localResources = paramContext.getResources();
    String str = null;
    switch (paramInt)
    {
    default:
    case 1:
    case 3:
    case 2:
    }
    while (true)
    {
      return str;
      str = localResources.getString(R.string.common_google_play_services_install_button);
      continue;
      str = localResources.getString(R.string.common_google_play_services_enable_button);
      continue;
      str = localResources.getString(R.string.common_google_play_services_update_button);
    }
  }

  // ERROR //
  private static byte[] a(PackageInfo paramPackageInfo, byte[][] paramArrayOfByte)
  {
    // Byte code:
    //   0: ldc 161
    //   2: invokestatic 167	java/security/cert/CertificateFactory:getInstance	(Ljava/lang/String;)Ljava/security/cert/CertificateFactory;
    //   5: astore 5
    //   7: aload_0
    //   8: getfield 173	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   11: arraylength
    //   12: iconst_1
    //   13: if_icmpeq +34 -> 47
    //   16: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   19: ldc 175
    //   21: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   24: pop
    //   25: aconst_null
    //   26: astore 4
    //   28: aload 4
    //   30: areturn
    //   31: astore_2
    //   32: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   35: ldc 183
    //   37: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   40: pop
    //   41: aconst_null
    //   42: astore 4
    //   44: goto -16 -> 28
    //   47: new 185	java/io/ByteArrayInputStream
    //   50: dup
    //   51: aload_0
    //   52: getfield 173	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   55: iconst_0
    //   56: aaload
    //   57: invokevirtual 191	android/content/pm/Signature:toByteArray	()[B
    //   60: invokespecial 194	java/io/ByteArrayInputStream:<init>	([B)V
    //   63: astore 6
    //   65: aload 5
    //   67: aload 6
    //   69: invokevirtual 198	java/security/cert/CertificateFactory:generateCertificate	(Ljava/io/InputStream;)Ljava/security/cert/Certificate;
    //   72: checkcast 200	java/security/cert/X509Certificate
    //   75: astore 9
    //   77: aload 9
    //   79: invokevirtual 203	java/security/cert/X509Certificate:checkValidity	()V
    //   82: aload_0
    //   83: getfield 173	android/content/pm/PackageInfo:signatures	[Landroid/content/pm/Signature;
    //   86: iconst_0
    //   87: aaload
    //   88: invokevirtual 191	android/content/pm/Signature:toByteArray	()[B
    //   91: astore 14
    //   93: iconst_0
    //   94: istore 15
    //   96: iload 15
    //   98: aload_1
    //   99: arraylength
    //   100: if_icmpge +83 -> 183
    //   103: aload_1
    //   104: iload 15
    //   106: aaload
    //   107: astore 17
    //   109: aload 17
    //   111: aload 14
    //   113: invokestatic 209	java/util/Arrays:equals	([B[B)Z
    //   116: ifeq +61 -> 177
    //   119: aload 17
    //   121: astore 4
    //   123: goto -95 -> 28
    //   126: astore 7
    //   128: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   131: ldc 211
    //   133: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   136: pop
    //   137: aconst_null
    //   138: astore 4
    //   140: goto -112 -> 28
    //   143: astore 12
    //   145: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   148: ldc 213
    //   150: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   153: pop
    //   154: aconst_null
    //   155: astore 4
    //   157: goto -129 -> 28
    //   160: astore 10
    //   162: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   165: ldc 215
    //   167: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   170: pop
    //   171: aconst_null
    //   172: astore 4
    //   174: goto -146 -> 28
    //   177: iinc 15 1
    //   180: goto -84 -> 96
    //   183: getstatic 26	com/google/android/gms/common/GooglePlayServicesUtil:TAG	Ljava/lang/String;
    //   186: new 78	java/lang/StringBuilder
    //   189: dup
    //   190: ldc 217
    //   192: invokespecial 83	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   195: aload 14
    //   197: iconst_0
    //   198: invokestatic 221	android/util/Base64:encodeToString	([BI)Ljava/lang/String;
    //   201: invokevirtual 89	java/lang/StringBuilder:append	(Ljava/lang/String;)Ljava/lang/StringBuilder;
    //   204: invokevirtual 92	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   207: invokestatic 181	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;)I
    //   210: pop
    //   211: aconst_null
    //   212: astore 4
    //   214: goto -186 -> 28
    //
    // Exception table:
    //   from	to	target	type
    //   0	7	31	java/security/cert/CertificateException
    //   65	77	126	java/security/cert/CertificateException
    //   77	82	143	java/security/cert/CertificateExpiredException
    //   77	82	160	java/security/cert/CertificateNotYetValidException
  }

  public static String b(Context paramContext, int paramInt1, int paramInt2)
  {
    Resources localResources1 = paramContext.getResources();
    String str = localResources1.getString(R.string.common_google_play_services_unknown_issue);
    switch (paramInt1)
    {
    default:
    case 1:
    case 3:
    case 2:
    }
    while (true)
    {
      return str;
      Resources localResources2 = paramContext.getResources();
      int i;
      label67: int j;
      if ((0xF & localResources2.getConfiguration().screenLayout) > 3)
      {
        i = 1;
        label121: int k;
        if ((Build.VERSION.SDK_INT < 11) || (i == 0))
        {
          Configuration localConfiguration = localResources2.getConfiguration();
          if (Build.VERSION.SDK_INT < 13)
            break label190;
          if (((0xF & localConfiguration.screenLayout) > 3) || (localConfiguration.smallestScreenWidthDp < 600))
            break label184;
          j = 1;
          k = 0;
          if (j == 0);
        }
        else
        {
          k = 1;
        }
        if (k == 0)
          break label196;
      }
      label184: label190: label196: for (str = localResources1.getString(R.string.common_google_play_services_install_text_tablet); d(paramInt2); str = localResources1.getString(R.string.common_google_play_services_install_text_phone))
      {
        str = str + " (via Bazaar)";
        break;
        i = 0;
        break label67;
        j = 0;
        break label121;
        j = 0;
        break label121;
      }
      str = localResources1.getString(R.string.common_google_play_services_enable_text);
      continue;
      str = localResources1.getString(R.string.common_google_play_services_update_text);
      if (d(paramInt2))
        str = str + " (via Bazaar)";
    }
  }

  private static boolean d(int paramInt)
  {
    boolean bool = true;
    if (paramInt == -1)
      paramInt = 2;
    switch (paramInt)
    {
    default:
      bool = false;
    case 1:
    case 0:
    case 2:
    }
    while (true)
    {
      return bool;
      if ("user".equals(Build.TYPE))
      {
        bool = false;
        continue;
        bool = false;
      }
    }
  }

  private static boolean e(Context paramContext)
  {
    try
    {
      byte[] arrayOfByte = a(paramContext.getPackageManager().getPackageInfo("com.google.android.apps.bazaar", 64), aI);
      bool = false;
      if (arrayOfByte != null)
        bool = true;
      return bool;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        boolean bool = false;
    }
  }

  public static Dialog getErrorDialog(int paramInt1, Activity paramActivity, int paramInt2)
  {
    AlertDialog.Builder localBuilder = new AlertDialog.Builder(paramActivity).setMessage(b(paramActivity, paramInt1, -1));
    aw localaw = new aw(paramActivity, a(paramActivity, paramInt1, -1), 0);
    String str = a(paramActivity, paramInt1);
    if (str != null)
      localBuilder.setPositiveButton(str, localaw);
    AlertDialog localAlertDialog = null;
    switch (paramInt1)
    {
    case 4:
    case 5:
    default:
      throw new IllegalArgumentException("Unexpected errorCode " + paramInt1);
    case 1:
      localAlertDialog = localBuilder.setTitle(R.string.common_google_play_services_install_title).create();
    case 0:
    case 6:
    case 3:
    case 2:
    case 9:
    case 7:
    case 8:
    }
    while (true)
    {
      return localAlertDialog;
      localAlertDialog = localBuilder.setTitle(R.string.common_google_play_services_enable_title).create();
      continue;
      localAlertDialog = localBuilder.setTitle(R.string.common_google_play_services_update_title).create();
      continue;
      Log.e(TAG, "Google Play services is invalid. Cannot recover.");
      localAlertDialog = null;
      continue;
      Log.e(TAG, "Network error occurred. Please retry request later.");
      localAlertDialog = null;
      continue;
      Log.e(TAG, "Internal error occurred. Please see logs for detailed information");
      localAlertDialog = null;
    }
  }

  public static Context getRemoteContext(Context paramContext)
  {
    try
    {
      Context localContext2 = paramContext.createPackageContext("com.google.android.gms", 3);
      localContext1 = localContext2;
      return localContext1;
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException)
    {
      while (true)
        Context localContext1 = null;
    }
  }

  public static int isGooglePlayServicesAvailable(Context paramContext)
  {
    int i = 9;
    PackageManager localPackageManager = paramContext.getPackageManager();
    try
    {
      PackageInfo localPackageInfo1 = localPackageManager.getPackageInfo("com.android.vending", 64);
      arrayOfByte = a(localPackageInfo1, aE);
      if (arrayOfByte == null)
      {
        Log.w(TAG, "Google Play Store signature invalid.");
        return i;
      }
    }
    catch (PackageManager.NameNotFoundException localNameNotFoundException1)
    {
      label207: 
      while (true)
      {
        byte[] arrayOfByte;
        Log.w(TAG, "Google Play Store is missing.");
        i = 1;
        continue;
        PackageInfo localPackageInfo2;
        try
        {
          localPackageInfo2 = localPackageManager.getPackageInfo("com.google.android.gms", 64);
          if (a(localPackageInfo2, new byte[][] { arrayOfByte }) != null)
            break label119;
          Log.w(TAG, "Google Play services signature invalid.");
        }
        catch (PackageManager.NameNotFoundException localNameNotFoundException2)
        {
          Log.w(TAG, "Google Play services is missing.");
          i = 1;
        }
        continue;
        label119: if (localPackageInfo2.versionCode < 2012100)
        {
          Log.w(TAG, "Google Play services out of date.  Requires 2012100 but found " + localPackageInfo2.versionCode);
          i = 2;
        }
        else
        {
          try
          {
            ApplicationInfo localApplicationInfo = localPackageManager.getApplicationInfo("com.google.android.gms", 0);
            if (localApplicationInfo.enabled)
              break label207;
            i = 3;
          }
          catch (PackageManager.NameNotFoundException localNameNotFoundException3)
          {
            Log.wtf(TAG, "Google Play services missing when getting application info.");
            localNameNotFoundException3.printStackTrace();
            i = 1;
          }
          continue;
          i = 0;
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.gms.common.GooglePlayServicesUtil
 * JD-Core Version:    0.6.2
 */