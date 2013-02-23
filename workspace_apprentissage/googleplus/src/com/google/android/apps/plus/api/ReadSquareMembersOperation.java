package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.AudienceData;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ReadSquareMembersOzRequest;
import com.google.api.services.plusi.model.ReadSquareMembersOzRequestJson;
import com.google.api.services.plusi.model.ReadSquareMembersOzResponse;
import com.google.api.services.plusi.model.ReadSquareMembersOzResponseJson;

public final class ReadSquareMembersOperation extends PlusiOperation<ReadSquareMembersOzRequest, ReadSquareMembersOzResponse>
{
  private final String mContinuationtoken;
  private final String mSquareId;
  private AudienceData mSquareMembers;

  public ReadSquareMembersOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "readsquaremembers", ReadSquareMembersOzRequestJson.getInstance(), ReadSquareMembersOzResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSquareId = paramString1;
    this.mContinuationtoken = paramString2;
  }

  public final AudienceData getSquareMembers()
  {
    return this.mSquareMembers;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.ReadSquareMembersOperation
 * JD-Core Version:    0.6.2
 */