package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class UpdateConversationOperation extends RealTimeChatOperation
{
  private final long mConversationRowId;
  private Boolean mMuted = null;
  private String mName = null;

  public UpdateConversationOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong;
  }

  public final void execute()
  {
    if (this.mName != null)
      EsConversationsData.updateConversationNameLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mName, this.mOperationState);
    if (this.mMuted != null)
      EsConversationsData.updateConversationMutedLocally(this.mContext, this.mAccount, this.mConversationRowId, this.mMuted.booleanValue(), this.mOperationState);
  }

  public final void setMuted(boolean paramBoolean)
  {
    this.mMuted = Boolean.valueOf(paramBoolean);
  }

  public final void setName(String paramString)
  {
    this.mName = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.UpdateConversationOperation
 * JD-Core Version:    0.6.2
 */