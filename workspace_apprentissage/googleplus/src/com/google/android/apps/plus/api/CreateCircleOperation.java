package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.CreateCircleRequest;
import com.google.api.services.plusi.model.CreateCircleRequestJson;
import com.google.api.services.plusi.model.CreateCircleResponse;
import com.google.api.services.plusi.model.CreateCircleResponseJson;

public final class CreateCircleOperation extends PlusiOperation<CreateCircleRequest, CreateCircleResponse>
{
  private final String mCircleName;
  private final boolean mJustFollowing;

  public CreateCircleOperation(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "createcircle", CreateCircleRequestJson.getInstance(), CreateCircleResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCircleName = paramString;
    this.mJustFollowing = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.CreateCircleOperation
 * JD-Core Version:    0.6.2
 */