package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ModifyCirclePropertiesRequest;
import com.google.api.services.plusi.model.ModifyCirclePropertiesRequestJson;
import com.google.api.services.plusi.model.ModifyCirclePropertiesResponse;
import com.google.api.services.plusi.model.ModifyCirclePropertiesResponseJson;

public final class ModifyCirclePropertiesOperation extends PlusiOperation<ModifyCirclePropertiesRequest, ModifyCirclePropertiesResponse>
{
  private final String mCircleId;
  private final String mCircleName;
  private final boolean mJustFollowing;

  public ModifyCirclePropertiesOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "modifycircleproperties", ModifyCirclePropertiesRequestJson.getInstance(), ModifyCirclePropertiesResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCircleId = paramString1;
    this.mCircleName = paramString2;
    this.mJustFollowing = paramBoolean;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ModifyCirclePropertiesOperation
 * JD-Core Version:    0.6.2
 */