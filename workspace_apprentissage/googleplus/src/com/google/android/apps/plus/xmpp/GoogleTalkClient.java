package com.google.android.apps.plus.xmpp;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.res.Resources;
import android.preference.PreferenceManager;
import android.util.Base64;
import android.util.Log;
import com.google.android.apps.plus.R.bool;
import com.google.android.apps.plus.R.string;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.AuthData;
import com.google.android.apps.plus.util.EsLog;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStreamWriter;
import java.net.Socket;

public abstract class GoogleTalkClient
{
  private boolean mActive;
  private final String mAddress;
  private final String mBackendAddress;
  private final Context mContext;
  private final boolean mDebugModeEnabled;
  private final EsAccount mEsAccount;
  private String mGoogleToken;
  private String mJabberId;
  private final String mResource;
  private Socket mSocket;
  private GoogleTalkThread mThread;
  private BufferedWriter mWriter;

  public GoogleTalkClient(EsAccount paramEsAccount, Context paramContext, String paramString1, String paramString2, String paramString3)
  {
    this.mEsAccount = paramEsAccount;
    this.mContext = paramContext;
    this.mAddress = paramString1;
    this.mBackendAddress = paramString2;
    this.mResource = paramString3;
    this.mActive = false;
    Resources localResources = paramContext.getResources();
    this.mDebugModeEnabled = PreferenceManager.getDefaultSharedPreferences(paramContext).getBoolean(localResources.getString(R.string.realtimechat_notify_setting_key), localResources.getBoolean(R.bool.realtimechat_notify_setting_default_value));
  }

  private void resetWriter()
    throws IOException
  {
    this.mWriter = new BufferedWriter(new OutputStreamWriter(this.mSocket.getOutputStream()));
  }

  public final boolean active()
  {
    return this.mActive;
  }

  public final void connect()
  {
    try
    {
      this.mActive = true;
      if (this.mThread == null)
      {
        this.mThread = new GoogleTalkThread();
        this.mThread.start();
      }
      return;
    }
    finally
    {
      localObject = finally;
      throw localObject;
    }
  }

  public void disconnect()
  {
    disconnect(1);
  }

  // ERROR //
  public final void disconnect(int paramInt)
  {
    // Byte code:
    //   0: aload_0
    //   1: monitorenter
    //   2: ldc 169
    //   4: iconst_3
    //   5: invokestatic 175	com/google/android/apps/plus/util/EsLog:isLoggable	(Ljava/lang/String;I)Z
    //   8: ifeq +26 -> 34
    //   11: ldc 169
    //   13: new 193	java/lang/StringBuilder
    //   16: dup
    //   17: ldc_w 257
    //   20: invokespecial 198	java/lang/StringBuilder:<init>	(Ljava/lang/String;)V
    //   23: iload_1
    //   24: invokevirtual 260	java/lang/StringBuilder:append	(I)Ljava/lang/StringBuilder;
    //   27: invokevirtual 205	java/lang/StringBuilder:toString	()Ljava/lang/String;
    //   30: invokestatic 208	android/util/Log:d	(Ljava/lang/String;Ljava/lang/String;)I
    //   33: pop
    //   34: aload_0
    //   35: iconst_0
    //   36: putfield 40	com/google/android/apps/plus/xmpp/GoogleTalkClient:mActive	Z
    //   39: aload_0
    //   40: getfield 248	com/google/android/apps/plus/xmpp/GoogleTalkClient:mThread	Lcom/google/android/apps/plus/xmpp/GoogleTalkClient$GoogleTalkThread;
    //   43: ifnull +41 -> 84
    //   46: aload_0
    //   47: getfield 248	com/google/android/apps/plus/xmpp/GoogleTalkClient:mThread	Lcom/google/android/apps/plus/xmpp/GoogleTalkClient$GoogleTalkThread;
    //   50: invokevirtual 263	com/google/android/apps/plus/xmpp/GoogleTalkClient$GoogleTalkThread:setDisconnected	()V
    //   53: aload_0
    //   54: getfield 94	com/google/android/apps/plus/xmpp/GoogleTalkClient:mSocket	Ljava/net/Socket;
    //   57: astore_3
    //   58: aload_3
    //   59: ifnull +10 -> 69
    //   62: aload_0
    //   63: getfield 94	com/google/android/apps/plus/xmpp/GoogleTalkClient:mSocket	Ljava/net/Socket;
    //   66: invokevirtual 266	java/net/Socket:close	()V
    //   69: aload_0
    //   70: aconst_null
    //   71: putfield 94	com/google/android/apps/plus/xmpp/GoogleTalkClient:mSocket	Ljava/net/Socket;
    //   74: aload_0
    //   75: aconst_null
    //   76: putfield 248	com/google/android/apps/plus/xmpp/GoogleTalkClient:mThread	Lcom/google/android/apps/plus/xmpp/GoogleTalkClient$GoogleTalkThread;
    //   79: aload_0
    //   80: iload_1
    //   81: invokevirtual 269	com/google/android/apps/plus/xmpp/GoogleTalkClient:onDisconnected	(I)V
    //   84: aload_0
    //   85: monitorexit
    //   86: return
    //   87: astore_2
    //   88: aload_0
    //   89: monitorexit
    //   90: aload_2
    //   91: athrow
    //   92: astore 4
    //   94: goto -25 -> 69
    //
    // Exception table:
    //   from	to	target	type
    //   2	58	87	finally
    //   62	69	87	finally
    //   69	84	87	finally
    //   62	69	92	java/io/IOException
  }

  public final EsAccount getAccount()
  {
    return this.mEsAccount;
  }

  public final Context getContext()
  {
    return this.mContext;
  }

  protected abstract void onConnected();

  protected abstract void onDisconnected(int paramInt);

  protected abstract void onMessageReceived(byte[] paramArrayOfByte);

  public final boolean sendMessage(byte[] paramArrayOfByte)
  {
    String str1 = this.mJabberId;
    boolean bool = false;
    if (str1 != null)
    {
      String str2 = this.mJabberId.split("/")[0];
      String str3 = this.mJabberId;
      String str4 = this.mAddress;
      String str5 = Base64.encodeToString(paramArrayOfByte, 0);
      bool = write("<message to='" + str2 + "' from='" + str3 + "' type='headline'><push xmlns='google:push' channel='realtime-chat'><recipient to='" + str4 + "' data=''/><data>" + str5 + "</data></push></message>");
    }
    return bool;
  }

  public final boolean write(String paramString)
  {
    try
    {
      this.mWriter.write(paramString);
      this.mWriter.flush();
      bool = true;
      return bool;
    }
    catch (IOException localIOException)
    {
      while (true)
      {
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "IOException while writing message");
        disconnect(6);
        boolean bool = false;
      }
    }
    finally
    {
    }
  }

  private final class GoogleTalkThread extends Thread
  {
    private boolean mConnected = true;

    public GoogleTalkThread()
    {
    }

    public final void run()
    {
      try
      {
        GoogleTalkClient.access$002(GoogleTalkClient.this, AuthData.getAuthToken(GoogleTalkClient.this.mContext, GoogleTalkClient.this.mEsAccount.getName(), "webupdates"));
        if (GoogleTalkClient.this.mGoogleToken == null)
        {
          if (EsLog.isLoggable("GoogleTalkClient", 3))
            Log.d("GoogleTalkClient", "authentication failed, null token");
          GoogleTalkClient.this.disconnect(3);
          return;
        }
      }
      catch (Exception localException1)
      {
        while (true)
        {
          GoogleTalkClient.this.disconnect(3);
          if (EsLog.isLoggable("GoogleTalkClient", 3))
            Log.d("GoogleTalkClient", "authentication failed", localException1);
          localException1.printStackTrace();
        }
        if (EsLog.isLoggable("GoogleTalkClient", 3))
          Log.d("GoogleTalkClient", "token " + GoogleTalkClient.this.mGoogleToken);
      }
      while (true)
      {
        MessageReader localMessageReader;
        try
        {
          GoogleTalkClient.access$302(GoogleTalkClient.this, new Socket("talk.google.com", 5222));
          try
          {
            GoogleTalkClient.this.resetWriter();
            GoogleTalkClient.access$500(GoogleTalkClient.this);
            if (this.mConnected)
            {
              localMessageReader = new MessageReader(GoogleTalkClient.this.mSocket.getInputStream(), GoogleTalkClient.this.mDebugModeEnabled);
              switch (GoogleTalkClient.1.$SwitchMap$com$google$android$apps$plus$xmpp$MessageReader$Event[localMessageReader.read().ordinal()])
              {
              case 1:
                if (EsLog.isLoggable("GoogleTalkClient", 4))
                  Log.i("GoogleTalkClient", "end of stream");
                GoogleTalkClient.this.disconnect(4);
                continue;
              case 2:
              case 3:
              case 4:
              case 5:
              case 6:
              case 7:
              case 8:
              case 9:
              case 10:
              }
            }
          }
          catch (Exception localException3)
          {
            if (EsLog.isLoggable("GoogleTalkClient", 5))
              Log.w("GoogleTalkClient", "Exception reading data", localException3);
            GoogleTalkClient.this.disconnect(6);
          }
          if (!EsLog.isLoggable("GoogleTalkClient", 3))
            break;
          Log.d("GoogleTalkClient", "thread finished");
        }
        catch (Exception localException2)
        {
          GoogleTalkClient.this.disconnect(2);
        }
        break;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "unexpected features");
        GoogleTalkClient.this.disconnect(5);
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "Authentication failed");
        AuthData.invalidateAuthToken(GoogleTalkClient.this.mContext, GoogleTalkClient.this.mEsAccount.getName(), "webupdates");
        GoogleTalkClient.access$002(GoogleTalkClient.this, null);
        GoogleTalkClient.this.disconnect(3);
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "TLS required");
        GoogleTalkClient.this.write("<starttls xmlns='urn:ietf:params:xml:ns:xmpp-tls'/>");
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "Proceed with TLS");
        GoogleTalkClient.access$700(GoogleTalkClient.this);
        GoogleTalkClient.access$500(GoogleTalkClient.this);
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "Authenticated required");
        GoogleTalkClient.this.write(Commands.authenticate(GoogleTalkClient.this.mGoogleToken));
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "Authenticated successfully");
        GoogleTalkClient.access$500(GoogleTalkClient.this);
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "stream reaqy");
        GoogleTalkClient localGoogleTalkClient = GoogleTalkClient.this;
        String str = GoogleTalkClient.this.mResource;
        localGoogleTalkClient.write("<iq type='set'><bind xmlns='urn:ietf:params:xml:ns:xmpp-bind'><resource>" + str + "</resource></bind></iq>");
        continue;
        if (EsLog.isLoggable("GoogleTalkClient", 4))
          Log.i("GoogleTalkClient", "jid available");
        GoogleTalkClient.access$900(GoogleTalkClient.this, localMessageReader.getEventData());
        continue;
        GoogleTalkClient.this.onMessageReceived(Base64.decode(localMessageReader.getEventData(), 0));
      }
    }

    public final void setDisconnected()
    {
      this.mConnected = false;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.xmpp.GoogleTalkClient
 * JD-Core Version:    0.6.2
 */