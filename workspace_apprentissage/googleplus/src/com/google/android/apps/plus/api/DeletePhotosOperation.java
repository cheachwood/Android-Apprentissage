package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeletePhotosRequest;
import com.google.api.services.plusi.model.DeletePhotosRequestJson;
import com.google.api.services.plusi.model.DeletePhotosResponse;
import com.google.api.services.plusi.model.DeletePhotosResponseJson;
import java.util.List;

public final class DeletePhotosOperation extends PlusiOperation<DeletePhotosRequest, DeletePhotosResponse>
{
  private final List<Long> mPhotoIds;

  public DeletePhotosOperation(Context paramContext, EsAccount paramEsAccount, List<Long> paramList, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "deletephotos", DeletePhotosRequestJson.getInstance(), DeletePhotosResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPhotoIds = paramList;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeletePhotosOperation
 * JD-Core Version:    0.6.2
 */