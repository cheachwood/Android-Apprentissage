package com.google.android.apps.plus.realtimechat;

import android.content.Context;
import com.google.android.apps.plus.content.EsAccount;
import com.google.wireless.realtimechat.proto.Client.BunchClientRequest;

public final class SetAclOperation extends RealTimeChatOperation
{
  private int mAcl;

  public SetAclOperation(Context paramContext, EsAccount paramEsAccount, int paramInt)
  {
    super(paramContext, paramEsAccount);
    this.mAcl = paramInt;
  }

  public final void execute()
  {
    Client.BunchClientRequest localBunchClientRequest = BunchCommands.setAcl(this.mAcl);
    this.mOperationState.addRequest(localBunchClientRequest);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.realtimechat.SetAclOperation
 * JD-Core Version:    0.6.2
 */