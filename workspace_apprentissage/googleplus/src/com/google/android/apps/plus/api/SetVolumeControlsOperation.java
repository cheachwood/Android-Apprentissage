package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SetVolumeControlsRequest;
import com.google.api.services.plusi.model.SetVolumeControlsRequestJson;
import com.google.api.services.plusi.model.SetVolumeControlsResponse;
import com.google.api.services.plusi.model.SetVolumeControlsResponseJson;
import java.util.HashMap;

public final class SetVolumeControlsOperation extends PlusiOperation<SetVolumeControlsRequest, SetVolumeControlsResponse>
{
  private String mCircleId;
  private HashMap<String, Integer> mCircleToVolumeMap;
  private int mVolume;

  public SetVolumeControlsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, int paramInt)
  {
    super(paramContext, paramEsAccount, "setvolumecontrols", SetVolumeControlsRequestJson.getInstance(), SetVolumeControlsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCircleId = paramString;
    this.mVolume = paramInt;
  }

  public SetVolumeControlsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, HashMap<String, Integer> paramHashMap)
  {
    super(paramContext, paramEsAccount, "setvolumecontrols", SetVolumeControlsRequestJson.getInstance(), SetVolumeControlsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mCircleToVolumeMap = paramHashMap;
  }

  private static String volumeIntToString(int paramInt)
  {
    String str;
    switch (paramInt)
    {
    default:
      str = null;
    case 0:
    case 1:
    case 2:
    case 3:
    case 4:
    }
    while (true)
    {
      return str;
      str = "NONE";
      continue;
      str = "LESS";
      continue;
      str = "NORMAL";
      continue;
      str = "MORE";
      continue;
      str = "NOTIFY";
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetVolumeControlsOperation
 * JD-Core Version:    0.6.2
 */