package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeletePlaceReviewRequest;
import com.google.api.services.plusi.model.DeletePlaceReviewRequestJson;
import com.google.api.services.plusi.model.DeletePlaceReviewResponse;
import com.google.api.services.plusi.model.DeletePlaceReviewResponseJson;

public final class DeleteReviewOperation extends PlusiOperation<DeletePlaceReviewRequest, DeletePlaceReviewResponse>
{
  private String cid;

  public DeleteReviewOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString)
  {
    super(paramContext, paramEsAccount, "deleteplacereview", DeletePlaceReviewRequestJson.getInstance(), DeletePlaceReviewResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.cid = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeleteReviewOperation
 * JD-Core Version:    0.6.2
 */