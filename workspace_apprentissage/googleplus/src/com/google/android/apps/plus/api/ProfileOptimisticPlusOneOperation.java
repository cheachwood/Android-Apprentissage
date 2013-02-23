package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPeopleData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;

public final class ProfileOptimisticPlusOneOperation extends PlusOneOperation
{
  public ProfileOptimisticPlusOneOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, paramIntent, paramOperationListener, "ENTITY", paramString, paramBoolean);
  }

  protected final void onFailure()
  {
    Context localContext = this.mContext;
    EsAccount localEsAccount = this.mAccount;
    String str = this.mItemId;
    if (!this.mIsPlusOne);
    for (boolean bool = true; ; bool = false)
    {
      EsPeopleData.changePlusOneData(localContext, localEsAccount, str, bool);
      return;
    }
  }

  protected final void onPopulateRequest()
  {
    EsPeopleData.changePlusOneData(this.mContext, this.mAccount, this.mItemId, this.mIsPlusOne);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ProfileOptimisticPlusOneOperation
 * JD-Core Version:    0.6.2
 */