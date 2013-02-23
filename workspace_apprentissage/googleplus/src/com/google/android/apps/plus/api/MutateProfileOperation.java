package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.MutateProfileRequest;
import com.google.api.services.plusi.model.MutateProfileRequestJson;
import com.google.api.services.plusi.model.MutateProfileResponse;
import com.google.api.services.plusi.model.MutateProfileResponseJson;
import com.google.api.services.plusi.model.SimpleProfile;
import java.util.HashMap;

public final class MutateProfileOperation extends PlusiOperation<MutateProfileRequest, MutateProfileResponse>
{
  private static HashMap<String, Integer> sErrorCodeMap;
  private SimpleProfile mProfileUpdates;

  public MutateProfileOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, SimpleProfile paramSimpleProfile)
  {
    super(paramContext, paramEsAccount, "mutateprofile", MutateProfileRequestJson.getInstance(), MutateProfileResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mProfileUpdates = paramSimpleProfile;
  }

  public static final class MutateProfileException extends ProtocolException
  {
    public MutateProfileException(MutateProfileResponse paramMutateProfileResponse)
    {
      super(paramMutateProfileResponse.errorMessage);
    }
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.MutateProfileOperation
 * JD-Core Version:    0.6.2
 */