package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class ReplyToInvitationOperation extends RealTimeChatOperation
{
  private boolean mAccept;
  private long mConversationRowId;
  private String mInviterId;

  ReplyToInvitationOperation(Context paramContext, EsAccount paramEsAccount, long paramLong, String paramString, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
    this.mInviterId = paramString;
    this.mAccept = paramBoolean;
  }

  public final void execute()
  {
    if (this.mAccept)
      EsConversationsData.acceptConversationLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mInviterId, this.mOperationState);
    while (true)
    {
      RealTimeChatNotifications.update(this.mContext, this.mAccount, true);
      return;
      Context localContext = this.mContext;
      EsAccount localEsAccount = this.mAccount;
      String str = this.mInviterId;
      EsConversationsData.removeAllConversationsFromInviterLocally$37a126b9(localContext, localEsAccount, str);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.ReplyToInvitationOperation
 * JD-Core Version:    0.6.2
 */