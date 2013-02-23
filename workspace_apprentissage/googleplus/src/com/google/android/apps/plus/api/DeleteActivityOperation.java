package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeleteActivityRequest;
import com.google.api.services.plusi.model.DeleteActivityRequestJson;
import com.google.api.services.plusi.model.DeleteActivityResponse;
import com.google.api.services.plusi.model.DeleteActivityResponseJson;

public final class DeleteActivityOperation extends PlusiOperation<DeleteActivityRequest, DeleteActivityResponse>
{
  private final String mActivityId;

  public DeleteActivityOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString)
  {
    super(paramContext, paramEsAccount, "deleteactivity", DeleteActivityRequestJson.getInstance(), DeleteActivityResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mActivityId = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeleteActivityOperation
 * JD-Core Version:    0.6.2
 */