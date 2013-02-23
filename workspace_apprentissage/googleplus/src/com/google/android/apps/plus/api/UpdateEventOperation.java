package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.PlusEvent;
import com.google.api.services.plusi.model.UpdateEventRequest;
import com.google.api.services.plusi.model.UpdateEventRequestJson;
import com.google.api.services.plusi.model.UpdateEventResponse;
import com.google.api.services.plusi.model.UpdateEventResponseJson;

public final class UpdateEventOperation extends PlusiOperation<UpdateEventRequest, UpdateEventResponse>
{
  private PlusEvent mPlusEvent;

  public UpdateEventOperation(Context paramContext, EsAccount paramEsAccount, PlusEvent paramPlusEvent, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "updateevent", UpdateEventRequestJson.getInstance(), UpdateEventResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mPlusEvent = paramPlusEvent;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.UpdateEventOperation
 * JD-Core Version:    0.6.2
 */