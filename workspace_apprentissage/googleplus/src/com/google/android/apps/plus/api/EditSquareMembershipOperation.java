package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EditSquareMembershipOzRequest;
import com.google.api.services.plusi.model.EditSquareMembershipOzRequestJson;
import com.google.api.services.plusi.model.EditSquareMembershipOzResponse;
import com.google.api.services.plusi.model.EditSquareMembershipOzResponseJson;

public final class EditSquareMembershipOperation extends PlusiOperation<EditSquareMembershipOzRequest, EditSquareMembershipOzResponse>
{
  private final String mAction;
  private boolean mIsBlockingModerator;
  private final String mSquareId;

  public EditSquareMembershipOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "editsquaremembership", EditSquareMembershipOzRequestJson.getInstance(), EditSquareMembershipOzResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mSquareId = paramString1;
    this.mAction = paramString2;
  }

  public final boolean getIsBlockingModerator()
  {
    return this.mIsBlockingModerator;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EditSquareMembershipOperation
 * JD-Core Version:    0.6.2
 */