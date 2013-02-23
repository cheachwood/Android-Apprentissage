package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import com.google.android.apps.plus.util.StringUtils;
import com.google.wireless.realtimechat.proto.Client.ClientConversation;
import com.google.wireless.realtimechat.proto.Client.ClientConversation.Builder;
import com.google.wireless.realtimechat.proto.Data.ConversationType;
import com.google.wireless.realtimechat.proto.Data.Participant;
import java.util.Iterator;
import java.util.List;

public final class CreateConversationOperation extends RealTimeChatOperation
{
  AudienceData mAudience;
  Client.ClientConversation mConversation;
  Long mConversationRowId;
  long mMessageRowId;
  String mMessageText;
  int mResultCode;

  public CreateConversationOperation(Context paramContext, EsAccount paramEsAccount, AudienceData paramAudienceData, String paramString)
  {
    super(paramContext, paramEsAccount);
    this.mAudience = paramAudienceData;
    this.mMessageText = paramString;
    this.mResultCode = 1;
  }

  public final void execute()
  {
    boolean bool = true;
    ConnectivityManager localConnectivityManager = (ConnectivityManager)this.mContext.getSystemService("connectivity");
    if ((localConnectivityManager != null) && ((localConnectivityManager.getActiveNetworkInfo() == null) || (!localConnectivityManager.getActiveNetworkInfo().isConnectedOrConnecting())))
      bool = false;
    List localList = ParticipantUtils.getParticipantListFromAudience(this.mContext, this.mAccount, this.mAudience);
    if (localList.size() > 100)
    {
      this.mResultCode = 4;
      return;
    }
    Client.ClientConversation.Builder localBuilder = Client.ClientConversation.newBuilder();
    Iterator localIterator = localList.iterator();
    while (localIterator.hasNext())
      localBuilder.addParticipant((Data.Participant)localIterator.next());
    if (localList.size() > 1)
      localBuilder.setType(Data.ConversationType.GROUP);
    while (true)
    {
      localBuilder.setId("c:" + StringUtils.randomString(32));
      this.mConversation = localBuilder.build();
      Bundle localBundle = EsConversationsData.createConversationLocally(this.mContext, this.mAccount, this.mConversation, this.mMessageText, bool, this.mOperationState);
      this.mConversationRowId = Long.valueOf(localBundle.getLong("conversation_row_id"));
      this.mMessageRowId = localBundle.getLong("message_row_id");
      if (!bool)
        break;
      CheckIfFailedRunnable localCheckIfFailedRunnable = new CheckIfFailedRunnable((byte)0);
      new Handler(Looper.getMainLooper()).postDelayed(localCheckIfFailedRunnable, 10000L);
      break;
      localBuilder.setType(Data.ConversationType.ONE_TO_ONE);
    }
  }

  public final int getResultCode()
  {
    return this.mResultCode;
  }

  public final Object getResultValue()
  {
    return new ConversationResult(this.mConversationRowId, this.mConversation);
  }

  private final class CheckIfFailedRunnable
    implements Runnable
  {
    private CheckIfFailedRunnable()
    {
    }

    public final void run()
    {
      RealTimeChatService.checkMessageSent(CreateConversationOperation.this.mContext, CreateConversationOperation.this.mAccount, CreateConversationOperation.this.mMessageRowId, 0);
    }
  }

  public final class ConversationResult
  {
    public Client.ClientConversation mConversation;
    public Long mConversationRowId;

    ConversationResult(Long paramClientConversation, Client.ClientConversation arg3)
    {
      this.mConversationRowId = paramClientConversation;
      Object localObject;
      this.mConversation = localObject;
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.CreateConversationOperation
 * JD-Core Version:    0.6.2
 */