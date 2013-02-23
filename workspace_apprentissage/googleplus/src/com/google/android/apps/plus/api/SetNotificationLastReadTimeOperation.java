package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeRequest;
import com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeRequestJson;
import com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeResponse;
import com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeResponseJson;

public final class SetNotificationLastReadTimeOperation extends PlusiOperation<UpdateNotificationsLastReadTimeRequest, UpdateNotificationsLastReadTimeResponse>
{
  private final double mReadTimestamp;

  public SetNotificationLastReadTimeOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener, double paramDouble)
  {
    super(paramContext, paramEsAccount, "updatenotificationslastreadtime", UpdateNotificationsLastReadTimeRequestJson.getInstance(), UpdateNotificationsLastReadTimeResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mReadTimestamp = paramDouble;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SetNotificationLastReadTimeOperation
 * JD-Core Version:    0.6.2
 */