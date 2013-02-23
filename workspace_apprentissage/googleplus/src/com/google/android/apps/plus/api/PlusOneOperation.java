package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataPlusOne;
import com.google.api.services.plusi.model.PlusOneRequest;
import com.google.api.services.plusi.model.PlusOneRequestJson;
import com.google.api.services.plusi.model.PlusOneResponse;
import com.google.api.services.plusi.model.PlusOneResponseJson;

public class PlusOneOperation extends PlusiOperation<PlusOneRequest, PlusOneResponse>
{
  protected final boolean mIsPlusOne;
  protected final String mItemId;
  protected final String mItemType;

  public PlusOneOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString1, String paramString2, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, "plusone", PlusOneRequestJson.getInstance(), PlusOneResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mItemType = paramString1;
    this.mItemId = paramString2;
    this.mIsPlusOne = paramBoolean;
  }

  protected void onFailure()
  {
  }

  protected final void onHttpOperationComplete(int paramInt, String paramString, Exception paramException)
  {
    if ((paramInt != 200) || (paramException != null))
      onFailure();
    super.onHttpOperationComplete(paramInt, paramString, paramException);
  }

  protected void onPopulateRequest()
  {
  }

  protected void onSuccess(DataPlusOne paramDataPlusOne)
  {
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PlusOneOperation
 * JD-Core Version:    0.6.2
 */