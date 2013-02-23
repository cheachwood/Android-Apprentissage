package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class RequestMoreEventsOperation extends RealTimeChatOperation
{
  private long mConversationRowId;

  public RequestMoreEventsOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
  }

  public final void execute()
  {
    EsConversationsData.requestOlderEvents(this.mContext, this.mAccount, this.mConversationRowId, this.mOperationState);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RequestMoreEventsOperation
 * JD-Core Version:    0.6.2
 */