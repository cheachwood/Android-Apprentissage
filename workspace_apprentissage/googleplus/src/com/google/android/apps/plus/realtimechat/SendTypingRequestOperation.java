package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;
import com.google.wireless.realtimechat.proto.Client.Typing.Type;

public final class SendTypingRequestOperation extends RealTimeChatOperation
{
  final long mConversationRowId;
  final Client.Typing.Type mTypingType;

  public SendTypingRequestOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, Client.Typing.Type paramType)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
    this.mTypingType = paramType;
  }

  public final void execute()
  {
    EsConversationsData.sendTypingRequestLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mTypingType, this.mOperationState);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SendTypingRequestOperation
 * JD-Core Version:    0.6.2
 */