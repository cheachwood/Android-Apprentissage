package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class SendPresenceRequestOperation extends RealTimeChatOperation
{
  final long mConversationRowId;
  final boolean mIsPresent;
  final boolean mReciprocate;

  public SendPresenceRequestOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, boolean paramBoolean1, boolean paramBoolean2)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
    this.mIsPresent = paramBoolean1;
    this.mReciprocate = paramBoolean2;
  }

  public final void execute()
  {
    EsConversationsData.sendPresenceRequestLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mIsPresent, this.mReciprocate, this.mOperationState);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SendPresenceRequestOperation
 * JD-Core Version:    0.6.2
 */