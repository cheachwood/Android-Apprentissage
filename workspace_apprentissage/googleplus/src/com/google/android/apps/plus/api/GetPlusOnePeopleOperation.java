package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataPerson;
import com.google.api.services.plusi.model.GetPlusonePeopleRequest;
import com.google.api.services.plusi.model.GetPlusonePeopleRequestJson;
import com.google.api.services.plusi.model.GetPlusonePeopleResponse;
import com.google.api.services.plusi.model.GetPlusonePeopleResponseJson;
import java.util.List;

public final class GetPlusOnePeopleOperation extends PlusiOperation<GetPlusonePeopleRequest, GetPlusonePeopleResponse>
{
  private final int mNumPeopleToReturn;
  private List<DataPerson> mPeople;
  private final String mPlusOneId;

  public GetPlusOnePeopleOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, String paramString, int paramInt)
  {
    super(paramContext, paramEsAccount, "getplusonepeople", GetPlusonePeopleRequestJson.getInstance(), GetPlusonePeopleResponseJson.getInstance(), null, null);
    this.mPlusOneId = paramString;
    this.mNumPeopleToReturn = 50;
  }

  public final List<DataPerson> getPeople()
  {
    return this.mPeople;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetPlusOnePeopleOperation
 * JD-Core Version:    0.6.2
 */