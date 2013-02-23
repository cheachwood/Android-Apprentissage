package com.google.android.apps.plus.network;

import android.accounts.AuthenticatorException;
import android.accounts.OperationCanceledException;
import android.content.Context;
import android.text.TextUtils;
import com.google.android.apps.plus.util.AccountsUtil;
import com.google.android.apps.plus.util.Property;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

public final class ApiaryAuthDataFactory
{
  static final Map<String, ApiaryAuthData> sAuthDatas = new HashMap();

  public static ApiaryAuthData getAuthData(String paramString)
  {
    synchronized (sAuthDatas)
    {
      Object localObject2 = (ApiaryAuthData)sAuthDatas.get(paramString);
      if (localObject2 == null)
      {
        localObject2 = new ApiaryAuthDataImpl(paramString);
        sAuthDatas.put(paramString, localObject2);
      }
      return localObject2;
    }
  }

  public static abstract interface ApiaryAuthData
  {
    public abstract Long getAuthTime(String paramString);

    public abstract String getAuthToken(Context paramContext, String paramString)
      throws AuthenticatorException, OperationCanceledException, IOException;

    public abstract void invalidateAuthToken(Context paramContext, String paramString)
      throws OperationCanceledException, AuthenticatorException, IOException;
  }

  private static final class ApiaryAuthDataImpl
    implements ApiaryAuthDataFactory.ApiaryAuthData
  {
    private final String mScope;
    private final Map<String, Long> mTokenTimes = new HashMap();
    private final Map<String, String> mTokens = new HashMap();

    public ApiaryAuthDataImpl(String paramString)
    {
      this.mScope = paramString;
    }

    public final Long getAuthTime(String paramString)
    {
      if ((Property.ENABLE_DOGFOOD_FEATURES.getBoolean()) && (!TextUtils.isEmpty(Property.PLUS_APIARY_AUTH_TOKEN.get())));
      for (Long localLong = Long.valueOf(System.currentTimeMillis()); ; localLong = (Long)this.mTokenTimes.get(paramString))
        return localLong;
    }

    // ERROR //
    public final String getAuthToken(Context paramContext, String paramString)
      throws AuthenticatorException, OperationCanceledException, IOException
    {
      // Byte code:
      //   0: getstatic 35	com/google/android/apps/plus/util/Property:ENABLE_DOGFOOD_FEATURES	Lcom/google/android/apps/plus/util/Property;
      //   3: invokevirtual 39	com/google/android/apps/plus/util/Property:getBoolean	()Z
      //   6: ifeq +22 -> 28
      //   9: getstatic 42	com/google/android/apps/plus/util/Property:PLUS_APIARY_AUTH_TOKEN	Lcom/google/android/apps/plus/util/Property;
      //   12: invokevirtual 46	com/google/android/apps/plus/util/Property:get	()Ljava/lang/String;
      //   15: astore 5
      //   17: aload 5
      //   19: invokestatic 52	android/text/TextUtils:isEmpty	(Ljava/lang/CharSequence;)Z
      //   22: ifne +6 -> 28
      //   25: aload 5
      //   27: areturn
      //   28: aload_0
      //   29: monitorenter
      //   30: aload_0
      //   31: getfield 25	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokens	Ljava/util/Map;
      //   34: aload_2
      //   35: invokeinterface 69 2 0
      //   40: checkcast 79	java/lang/String
      //   43: astore 4
      //   45: aload 4
      //   47: ifnull +72 -> 119
      //   50: aload_0
      //   51: getfield 23	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokenTimes	Ljava/util/Map;
      //   54: aload 4
      //   56: invokeinterface 69 2 0
      //   61: checkcast 60	java/lang/Long
      //   64: astore 12
      //   66: aload 12
      //   68: ifnull +19 -> 87
      //   71: invokestatic 58	java/lang/System:currentTimeMillis	()J
      //   74: aload 12
      //   76: invokevirtual 82	java/lang/Long:longValue	()J
      //   79: lsub
      //   80: ldc2_w 83
      //   83: lcmp
      //   84: ifle +35 -> 119
      //   87: aload_0
      //   88: getfield 25	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokens	Ljava/util/Map;
      //   91: aload_2
      //   92: invokeinterface 87 2 0
      //   97: pop
      //   98: aload_0
      //   99: getfield 23	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokenTimes	Ljava/util/Map;
      //   102: aload 4
      //   104: invokeinterface 87 2 0
      //   109: pop
      //   110: aload_1
      //   111: aload 4
      //   113: invokestatic 93	com/google/android/apps/plus/util/AccountsUtil:invalidateAuthToken	(Landroid/content/Context;Ljava/lang/String;)V
      //   116: aconst_null
      //   117: astore 4
      //   119: aload_0
      //   120: monitorexit
      //   121: aload 4
      //   123: ifnonnull +90 -> 213
      //   126: invokestatic 58	java/lang/System:currentTimeMillis	()J
      //   129: invokestatic 64	java/lang/Long:valueOf	(J)Ljava/lang/Long;
      //   132: astore 6
      //   134: aload_1
      //   135: aload_2
      //   136: aload_0
      //   137: getfield 27	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mScope	Ljava/lang/String;
      //   140: invokestatic 96	com/google/android/apps/plus/util/AccountsUtil:getAuthToken	(Landroid/content/Context;Ljava/lang/String;Ljava/lang/String;)Ljava/lang/String;
      //   143: astore 4
      //   145: aload 4
      //   147: ifnull +66 -> 213
      //   150: aload_0
      //   151: monitorenter
      //   152: aload_0
      //   153: getfield 25	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokens	Ljava/util/Map;
      //   156: aload_2
      //   157: invokeinterface 87 2 0
      //   162: checkcast 79	java/lang/String
      //   165: astore 8
      //   167: aload 8
      //   169: ifnull +15 -> 184
      //   172: aload_0
      //   173: getfield 23	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokenTimes	Ljava/util/Map;
      //   176: aload 8
      //   178: invokeinterface 87 2 0
      //   183: pop
      //   184: aload_0
      //   185: getfield 25	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokens	Ljava/util/Map;
      //   188: aload_2
      //   189: aload 4
      //   191: invokeinterface 100 3 0
      //   196: pop
      //   197: aload_0
      //   198: getfield 23	com/google/android/apps/plus/network/ApiaryAuthDataFactory$ApiaryAuthDataImpl:mTokenTimes	Ljava/util/Map;
      //   201: aload 4
      //   203: aload 6
      //   205: invokeinterface 100 3 0
      //   210: pop
      //   211: aload_0
      //   212: monitorexit
      //   213: aload 4
      //   215: astore 5
      //   217: goto -192 -> 25
      //   220: astore_3
      //   221: aload_0
      //   222: monitorexit
      //   223: aload_3
      //   224: athrow
      //   225: astore 7
      //   227: aload_0
      //   228: monitorexit
      //   229: aload 7
      //   231: athrow
      //
      // Exception table:
      //   from	to	target	type
      //   30	121	220	finally
      //   152	213	225	finally
    }

    public final void invalidateAuthToken(Context paramContext, String paramString)
      throws OperationCanceledException, AuthenticatorException, IOException
    {
      if ((Property.ENABLE_DOGFOOD_FEATURES.getBoolean()) && (!TextUtils.isEmpty(Property.PLUS_APIARY_AUTH_TOKEN.get())));
      while (true)
      {
        return;
        try
        {
          String str = (String)this.mTokens.remove(paramString);
          if (str != null)
          {
            this.mTokenTimes.remove(str);
            AccountsUtil.invalidateAuthToken(paramContext, str);
          }
          if (str != null)
            continue;
          AccountsUtil.invalidateAuthToken(paramContext, AccountsUtil.getAuthToken(paramContext, paramString, this.mScope));
        }
        finally
        {
        }
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.network.ApiaryAuthDataFactory
 * JD-Core Version:    0.6.2
 */