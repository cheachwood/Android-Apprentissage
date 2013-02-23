package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class SetMessageFailedOperation extends RealTimeChatOperation
{
  long mConversationRowId;
  long mMessageRowId;

  public SetMessageFailedOperation(Context paramContext, EsAccount paramEsAccount, long paramLong1, long paramLong2)
  {
    super(paramContext, paramEsAccount);
    this.mConversationRowId = paramLong1;
    this.mMessageRowId = paramLong2;
  }

  public final void execute()
  {
    EsConversationsData.setMessageStatusLocally(this.mContext, this.mAccount, this.mMessageRowId, 8);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SetMessageFailedOperation
 * JD-Core Version:    0.6.2
 */