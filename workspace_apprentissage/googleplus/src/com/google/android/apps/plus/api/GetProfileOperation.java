package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.GetSimpleProfileRequest;
import com.google.api.services.plusi.model.GetSimpleProfileRequestJson;
import com.google.api.services.plusi.model.GetSimpleProfileResponse;
import com.google.api.services.plusi.model.GetSimpleProfileResponseJson;
import com.google.api.services.plusi.model.SimpleProfile;

public final class GetProfileOperation extends PlusiOperation<GetSimpleProfileRequest, GetSimpleProfileResponse>
{
  private final boolean mInsertInDatabase;
  private final String mOwnerId;
  private SimpleProfile mProfile;

  public GetProfileOperation(Context paramContext, EsAccount paramEsAccount, String paramString, boolean paramBoolean, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getsimpleprofile", GetSimpleProfileRequestJson.getInstance(), GetSimpleProfileResponseJson.getInstance(), null, null);
    this.mOwnerId = paramString;
    this.mInsertInDatabase = paramBoolean;
  }

  public final SimpleProfile getProfile()
  {
    return this.mProfile;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetProfileOperation
 * JD-Core Version:    0.6.2
 */