package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetVolumeControlsRequest;
import com.google.api.services.plusi.model.GetVolumeControlsRequestJson;
import com.google.api.services.plusi.model.GetVolumeControlsResponse;
import com.google.api.services.plusi.model.GetVolumeControlsResponseJson;
import com.google.api.services.plusi.model.VolumeControlMap;

public final class GetVolumeControlsOperation extends PlusiOperation<GetVolumeControlsRequest, GetVolumeControlsResponse>
{
  private VolumeControlMap mMap;

  public GetVolumeControlsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getvolumecontrols", GetVolumeControlsRequestJson.getInstance(), GetVolumeControlsResponseJson.getInstance(), null, paramOperationListener);
  }

  public final VolumeControlMap getVolumeControlMap()
  {
    return this.mMap;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetVolumeControlsOperation
 * JD-Core Version:    0.6.2
 */