package com.google.android.apps.plus.realtimechat;

import android.content.ContentResolver;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;
import android.util.Log;
import com.google.android.apps.plus.api.MediaRef;
import com.google.android.apps.plus.api.MediaRef.MediaType;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import com.google.android.apps.plus.service.EsService;
import com.google.android.apps.plus.service.EsServiceListener;
import com.google.android.apps.plus.util.EsLog;
import java.util.ArrayList;
import java.util.List;

public final class SendMessageGeneralOperation extends RealTimeChatOperation
{
  private long mConversationRowId;
  private String mImageUrl;
  private Long mMessageRowId;
  private Integer mRequestId;
  private boolean mRetry;
  private final ServiceListener mServiceListener = new ServiceListener();
  private String mText;

  public SendMessageGeneralOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mMessageRowId = Long.valueOf(paramLong);
    this.mRetry = true;
  }

  public SendMessageGeneralOperation(Context paramContext, EsAccount paramEsAccount, long paramLong1, String paramString, long paramLong2)
  {
    super(paramContext, paramEsAccount);
    if (paramLong2 != -1L)
      this.mMessageRowId = Long.valueOf(paramLong2);
    this.mConversationRowId = paramLong1;
    this.mImageUrl = paramString;
    this.mRetry = false;
  }

  public SendMessageGeneralOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString1, String paramString2)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
    this.mText = paramString1;
    this.mImageUrl = paramString2;
    this.mRetry = false;
  }

  private void checkMessageSentAfterTimeout()
  {
    new Handler(Looper.getMainLooper()).postDelayed(new Runnable()
    {
      public final void run()
      {
        Context localContext = SendMessageGeneralOperation.this.mContext;
        EsAccount localEsAccount = SendMessageGeneralOperation.this.mAccount;
        long l = SendMessageGeneralOperation.this.mMessageRowId.longValue();
        if (SendMessageGeneralOperation.this.mRetry);
        for (int i = 2; ; i = 0)
        {
          RealTimeChatService.checkMessageSent(localContext, localEsAccount, l, i);
          return;
        }
      }
    }
    , 10000L);
  }

  private boolean hasConnection()
  {
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.mContext.getSystemService("connectivity");
    boolean bool;
    if (localConnectivityManager == null)
      bool = true;
    while (true)
    {
      return bool;
      NetworkInfo localNetworkInfo = localConnectivityManager.getActiveNetworkInfo();
      if (localNetworkInfo != null)
        bool = localNetworkInfo.isConnectedOrConnecting();
      else
        bool = false;
    }
  }

  private void uploadPhoto(Bundle paramBundle, String paramString)
  {
    EsService.registerListener(this.mServiceListener);
    this.mMessageRowId = Long.valueOf(paramBundle.getLong("message_row_id"));
    String str1 = paramBundle.getString("conversation_id");
    final String str2 = paramBundle.getString("conversation_name");
    final String str3 = str1.replace(':', '_');
    final ArrayList localArrayList = new ArrayList();
    localArrayList.add("bunch");
    localArrayList.add(str3);
    Uri localUri = Uri.parse(paramString);
    String str4 = this.mContext.getContentResolver().getType(localUri);
    int i;
    if ((!TextUtils.isEmpty(str4)) && (str4.startsWith("video/")))
    {
      i = 1;
      if (i == 0)
        break label188;
    }
    label188: for (MediaRef.MediaType localMediaType = MediaRef.MediaType.VIDEO; ; localMediaType = MediaRef.MediaType.IMAGE)
    {
      final MediaRef localMediaRef = new MediaRef(this.mAccount.getGaiaId(), 0L, null, localUri, localMediaType);
      Handler localHandler = new Handler(Looper.getMainLooper());
      localHandler.post(new Runnable()
      {
        public final void run()
        {
          SendMessageGeneralOperation.access$002(SendMessageGeneralOperation.this, EsService.uploadImageThumbnail(SendMessageGeneralOperation.this.mContext, SendMessageGeneralOperation.this.mAccount, str2, str3, localArrayList, localMediaRef));
        }
      });
      return;
      i = 0;
      break;
    }
  }

  public final void execute()
  {
    if (this.mRetry)
    {
      if (EsLog.isLoggable("SendMessageGeneral", 3))
        Log.d("SendMessageGeneral", "retrySendMessage");
      boolean bool2 = hasConnection();
      Bundle localBundle = EsConversationsData.resendMessageLocally$65290203(this.mContext, this.mAccount, this.mMessageRowId.longValue(), this.mOperationState);
      String str2 = localBundle.getString("local_uri");
      if ((str2 != null) && (str2.startsWith("content://")))
        uploadPhoto(localBundle, str2);
      if (bool2)
        checkMessageSentAfterTimeout();
    }
    label299: 
    while (true)
    {
      return;
      if (EsLog.isLoggable("SendMessageGeneral", 3))
        Log.d("SendMessageGeneral", "sendOriginalMessage " + this.mText + " " + this.mImageUrl);
      boolean bool1 = hasConnection();
      if (this.mMessageRowId != null)
        EsConversationsData.updateMessageUriAndSendLocally$4f1d5505(this.mContext, this.mAccount, this.mConversationRowId, this.mMessageRowId.longValue(), this.mImageUrl, this.mOperationState);
      while (true)
      {
        if (!bool1)
          break label299;
        checkMessageSentAfterTimeout();
        break;
        if ((this.mImageUrl != null) && (this.mImageUrl.startsWith("content://")))
        {
          Context localContext = this.mContext;
          EsAccount localEsAccount = this.mAccount;
          long l = this.mConversationRowId;
          String str1 = this.mImageUrl;
          uploadPhoto(EsConversationsData.insertLocalPhotoLocally$341823c7(localContext, localEsAccount, l, str1), this.mImageUrl);
        }
        else
        {
          this.mMessageRowId = Long.valueOf(EsConversationsData.sendMessageLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mText, this.mImageUrl, bool1, this.mOperationState));
        }
      }
    }
  }

  public final Object getResultValue()
  {
    return this.mMessageRowId;
  }

  final class ServiceListener extends EsServiceListener
  {
    ServiceListener()
    {
    }

    public final void onImageThumbnailUploaded$51902fbe(int paramInt, String paramString)
    {
      if ((SendMessageGeneralOperation.this.mRequestId != null) && (paramInt == SendMessageGeneralOperation.this.mRequestId.intValue()))
      {
        if (paramString != null)
          break label83;
        RealTimeChatService.setMessageFailed(SendMessageGeneralOperation.this.mContext, SendMessageGeneralOperation.this.mAccount, SendMessageGeneralOperation.this.mConversationRowId, SendMessageGeneralOperation.this.mMessageRowId.longValue());
      }
      while (true)
      {
        new Handler().post(new Runnable()
        {
          public final void run()
          {
            EsService.unregisterListener(SendMessageGeneralOperation.this.mServiceListener);
          }
        });
        return;
        label83: RealTimeChatService.sendRemotePhoto(SendMessageGeneralOperation.this.mContext, SendMessageGeneralOperation.this.mAccount, SendMessageGeneralOperation.this.mConversationRowId, SendMessageGeneralOperation.this.mMessageRowId.longValue(), paramString);
      }
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SendMessageGeneralOperation
 * JD-Core Version:    0.6.2
 */