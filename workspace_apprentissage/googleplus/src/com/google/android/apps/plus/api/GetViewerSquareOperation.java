package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetViewerSquareOzRequest;
import com.google.api.services.plusi.model.GetViewerSquareOzRequestJson;
import com.google.api.services.plusi.model.GetViewerSquareOzResponse;
import com.google.api.services.plusi.model.GetViewerSquareOzResponseJson;

public final class GetViewerSquareOperation extends PlusiOperation<GetViewerSquareOzRequest, GetViewerSquareOzResponse>
{
  private final String mSquareId;

  public GetViewerSquareOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getviewersquare", GetViewerSquareOzRequestJson.getInstance(), GetViewerSquareOzResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSquareId = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetViewerSquareOperation
 * JD-Core Version:    0.6.2
 */