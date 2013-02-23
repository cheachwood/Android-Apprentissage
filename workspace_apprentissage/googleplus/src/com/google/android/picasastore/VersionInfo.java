package com.google.android.picasastore;

import android.util.Log;
import com.android.gallery3d.common.Utils;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.util.HashMap;

final class VersionInfo
{
  private String mFilepath;
  private HashMap<String, Integer> mMap = new HashMap();

  public VersionInfo(String paramString)
  {
    this.mFilepath = paramString;
    loadVersions();
  }

  private void loadVersions()
  {
    File localFile = new File(this.mFilepath);
    if (!localFile.exists());
    while (true)
    {
      return;
      BufferedReader localBufferedReader;
      try
      {
        localBufferedReader = new BufferedReader(new FileReader(localFile));
        try
        {
          String str1 = localBufferedReader.readLine();
          while (true)
          {
            if (str1 == null)
              break label172;
            int i = str1.lastIndexOf('=');
            String str2;
            String str3;
            if (i != -1)
            {
              str2 = str1.substring(0, i).trim();
              str3 = str1.substring(i + 1).trim();
            }
            try
            {
              int j = Integer.parseInt(str3);
              this.mMap.put(str2, Integer.valueOf(j));
              str1 = localBufferedReader.readLine();
            }
            catch (Throwable localThrowable2)
            {
              while (true)
                Log.w("VersionInfo", "fail parse line:" + str1, localThrowable2);
            }
          }
        }
        finally
        {
          Utils.closeSilently(localBufferedReader);
        }
      }
      catch (Throwable localThrowable1)
      {
        Log.w("VersionInfo", "cannot load version", localThrowable1);
      }
      continue;
      label172: Utils.closeSilently(localBufferedReader);
    }
  }

  public final int getVersion(String paramString)
  {
    Integer localInteger = (Integer)this.mMap.get(paramString);
    if (localInteger == null);
    for (int i = 0; ; i = localInteger.intValue())
      return i;
  }

  public final void setVersion(String paramString, int paramInt)
  {
    if (paramInt != 0);
    for (boolean bool = true; ; bool = false)
    {
      Utils.assertTrue(bool);
      this.mMap.put(paramString, Integer.valueOf(paramInt));
      return;
    }
  }

  // ERROR //
  public final void sync()
  {
    // Byte code:
    //   0: aconst_null
    //   1: astore_1
    //   2: new 124	java/io/PrintWriter
    //   5: dup
    //   6: new 28	java/io/File
    //   9: dup
    //   10: aload_0
    //   11: getfield 21	com/google/android/picasastore/VersionInfo:mFilepath	Ljava/lang/String;
    //   14: invokespecial 30	java/io/File:<init>	(Ljava/lang/String;)V
    //   17: invokespecial 125	java/io/PrintWriter:<init>	(Ljava/io/File;)V
    //   20: astore_2
    //   21: aload_0
    //   22: getfield 19	com/google/android/picasastore/VersionInfo:mMap	Ljava/util/HashMap;
    //   25: invokevirtual 129	java/util/HashMap:entrySet	()Ljava/util/Set;
    //   28: invokeinterface 135 1 0
    //   33: astore 6
    //   35: aload 6
    //   37: invokeinterface 140 1 0
    //   42: ifeq +78 -> 120
    //   45: aload 6
    //   47: invokeinterface 144 1 0
    //   52: checkcast 146	java/util/Map$Entry
    //   55: astore 7
    //   57: iconst_2
    //   58: anewarray 4	java/lang/Object
    //   61: astore 8
    //   63: aload 8
    //   65: iconst_0
    //   66: aload 7
    //   68: invokeinterface 149 1 0
    //   73: aastore
    //   74: aload 8
    //   76: iconst_1
    //   77: aload 7
    //   79: invokeinterface 152 1 0
    //   84: aastore
    //   85: aload_2
    //   86: ldc 154
    //   88: aload 8
    //   90: invokevirtual 158	java/io/PrintWriter:printf	(Ljava/lang/String;[Ljava/lang/Object;)Ljava/io/PrintWriter;
    //   93: pop
    //   94: aload_2
    //   95: invokevirtual 161	java/io/PrintWriter:println	()V
    //   98: goto -63 -> 35
    //   101: astore 4
    //   103: aload_2
    //   104: astore_1
    //   105: ldc 80
    //   107: ldc 163
    //   109: aload 4
    //   111: invokestatic 98	android/util/Log:w	(Ljava/lang/String;Ljava/lang/String;Ljava/lang/Throwable;)I
    //   114: pop
    //   115: aload_1
    //   116: invokestatic 104	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   119: return
    //   120: aload_2
    //   121: invokestatic 104	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   124: goto -5 -> 119
    //   127: astore_3
    //   128: aload_1
    //   129: invokestatic 104	com/android/gallery3d/common/Utils:closeSilently	(Ljava/io/Closeable;)V
    //   132: aload_3
    //   133: athrow
    //   134: astore_3
    //   135: aload_2
    //   136: astore_1
    //   137: goto -9 -> 128
    //   140: astore 4
    //   142: aconst_null
    //   143: astore_1
    //   144: goto -39 -> 105
    //
    // Exception table:
    //   from	to	target	type
    //   21	98	101	java/lang/Throwable
    //   2	21	127	finally
    //   105	115	127	finally
    //   21	98	134	finally
    //   2	21	140	java/lang/Throwable
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.picasastore.VersionInfo
 * JD-Core Version:    0.6.2
 */