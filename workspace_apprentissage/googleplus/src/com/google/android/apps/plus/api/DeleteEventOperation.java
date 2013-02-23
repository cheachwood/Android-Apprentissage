package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DeleteEventRequest;
import com.google.api.services.plusi.model.DeleteEventRequestJson;
import com.google.api.services.plusi.model.DeleteEventResponse;
import com.google.api.services.plusi.model.DeleteEventResponseJson;

public final class DeleteEventOperation extends PlusiOperation<DeleteEventRequest, DeleteEventResponse>
{
  private String mAuthKey;
  private String mEventId;

  public DeleteEventOperation(Context paramContext, EsAccount paramEsAccount, String paramString1, String paramString2, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "deleteevent", DeleteEventRequestJson.getInstance(), DeleteEventResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mEventId = paramString1;
    this.mAuthKey = paramString2;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.DeleteEventOperation
 * JD-Core Version:    0.6.2
 */