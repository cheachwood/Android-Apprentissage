package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.EventsHomeRequest;
import com.google.api.services.plusi.model.EventsHomeRequestJson;
import com.google.api.services.plusi.model.EventsHomeResponse;
import com.google.api.services.plusi.model.EventsHomeResponseJson;

public final class EventHomePageOperation extends PlusiOperation<EventsHomeRequest, EventsHomeResponse>
{
  public EventHomePageOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "eventshome", EventsHomeRequestJson.getInstance(), EventsHomeResponseJson.getInstance(), paramIntent, paramOperationListener);
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.EventHomePageOperation
 * JD-Core Version:    0.6.2
 */