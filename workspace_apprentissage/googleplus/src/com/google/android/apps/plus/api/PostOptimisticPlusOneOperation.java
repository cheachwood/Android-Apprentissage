package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsPostsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.service.EsService;
import com.google.api.services.plusi.model.DataPlusOne;

public final class PostOptimisticPlusOneOperation extends PlusOneOperation
{
  public PostOptimisticPlusOneOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, boolean paramBoolean)
  {
    super(paramContext, paramEsAccount, paramIntent, paramOperationListener, "TACO", paramString, paramBoolean);
  }

  protected final void onFailure()
  {
    Context localContext = this.mContext;
    EsAccount localEsAccount = this.mAccount;
    String str = this.mItemId;
    if (!this.mIsPlusOne);
    for (boolean bool = true; ; bool = false)
    {
      EsPostsData.plusOnePost(localContext, localEsAccount, str, bool);
      if (this.mIsPlusOne)
        EsService.updateNotifications(this.mContext, this.mAccount);
      return;
    }
  }

  protected final void onPopulateRequest()
  {
    EsPostsData.plusOnePost(this.mContext, this.mAccount, this.mItemId, this.mIsPlusOne);
    if (this.mIsPlusOne)
      EsService.updateNotifications(this.mContext, this.mAccount);
  }

  protected final void onSuccess(DataPlusOne paramDataPlusOne)
  {
    if (paramDataPlusOne != null)
      EsPostsData.updatePostPlusOneId(this.mContext, this.mAccount, this.mItemId, paramDataPlusOne.id);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PostOptimisticPlusOneOperation
 * JD-Core Version:    0.6.2
 */