package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class MarkConversationNotificationsSeenOperation extends RealTimeChatOperation
{
  long mConversationRowId;

  public MarkConversationNotificationsSeenOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
  }

  public final void execute()
  {
    Context localContext = this.mContext;
    EsAccount localEsAccount = this.mAccount;
    long l = this.mConversationRowId;
    EsConversationsData.markNotificationsSeenLocally$785b8fa1(localContext, localEsAccount, l);
    RealTimeChatNotifications.update(this.mContext, this.mAccount, true);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.MarkConversationNotificationsSeenOperation
 * JD-Core Version:    0.6.2
 */