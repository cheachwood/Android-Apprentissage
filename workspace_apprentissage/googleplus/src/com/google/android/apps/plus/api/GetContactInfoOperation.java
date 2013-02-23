package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataCirclePerson;
import com.google.api.services.plusi.model.LoadCircleMembersResponse;
import com.google.api.services.plusi.model.LoadCircleMembersResponseJson;
import com.google.api.services.plusi.model.LoadPeopleRequest;
import com.google.api.services.plusi.model.LoadPeopleRequestJson;

public final class GetContactInfoOperation extends PlusiOperation<LoadPeopleRequest, LoadCircleMembersResponse>
{
  private final String mGaiaId;
  private DataCirclePerson mPerson;

  public GetContactInfoOperation(Context paramContext, EsAccount paramEsAccount, String paramString, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "loadpeople", LoadPeopleRequestJson.getInstance(), LoadCircleMembersResponseJson.getInstance(), null, paramOperationListener);
    this.mGaiaId = paramString;
  }

  public final DataCirclePerson getPerson()
  {
    return this.mPerson;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetContactInfoOperation
 * JD-Core Version:    0.6.2
 */