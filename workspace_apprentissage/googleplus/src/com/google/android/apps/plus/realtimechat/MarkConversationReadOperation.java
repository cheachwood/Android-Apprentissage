package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class MarkConversationReadOperation extends RealTimeChatOperation
{
  long mConversationRowId;

  public MarkConversationReadOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
  }

  public final void execute()
  {
    EsConversationsData.markConversationReadLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mOperationState);
    if (this.mOperationState.shouldTriggerNotifications())
      RealTimeChatNotifications.update(this.mContext, this.mAccount, true);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.MarkConversationReadOperation
 * JD-Core Version:    0.6.2
 */