package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class LeaveConversationOperation extends RealTimeChatOperation
{
  long mConversationRowId;

  public LeaveConversationOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
  }

  public final void execute()
  {
    EsConversationsData.leaveConversationLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mOperationState);
    RealTimeChatNotifications.update(this.mContext, this.mAccount, true);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.LeaveConversationOperation
 * JD-Core Version:    0.6.2
 */