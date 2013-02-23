package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsConversationsData;

public final class RemoveMessageOperation extends RealTimeChatOperation
{
  long mMessageRowId;

  public RemoveMessageOperation(Context paramContext, EsAccount paramEsAccount, long paramLong)
  {
    super(paramContext, paramEsAccount);
    this.mMessageRowId = paramLong;
  }

  public final void execute()
  {
    EsConversationsData.removeMessageLocally(this.mContext, this.mAccount, this.mMessageRowId);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.RemoveMessageOperation
 * JD-Core Version:    0.6.2
 */