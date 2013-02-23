package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeleteCircleRequest;
import com.google.api.services.plusi.model.DeleteCircleRequestJson;
import com.google.api.services.plusi.model.DeleteCircleResponse;
import com.google.api.services.plusi.model.DeleteCircleResponseJson;
import java.util.ArrayList;

public final class DeleteCirclesOperation extends PlusiOperation<DeleteCircleRequest, DeleteCircleResponse>
{
  private final ArrayList<String> mCircleIds;

  public DeleteCirclesOperation(Context paramContext, EsAccount paramEsAccount, ArrayList<String> paramArrayList, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "deletecircle", DeleteCircleRequestJson.getInstance(), DeleteCircleResponseJson.getInstance(), null, null);
    this.mCircleIds = paramArrayList;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeleteCirclesOperation
 * JD-Core Version:    0.6.2
 */