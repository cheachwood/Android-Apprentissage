package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.content.EsAnalyticsData;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.ClientOzEvent;
import com.google.api.services.plusi.model.ClientOzExtension;
import com.google.api.services.plusi.model.PostClientLogsRequest;
import com.google.api.services.plusi.model.PostClientLogsRequestJson;
import com.google.api.services.plusi.model.PostClientLogsResponse;
import com.google.api.services.plusi.model.PostClientLogsResponseJson;
import java.util.List;

public final class PostClientLogsOperation extends PlusiOperation<PostClientLogsRequest, PostClientLogsResponse>
{
  private ClientOzExtension mClientOzExtension;

  public PostClientLogsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "postclientlogs", PostClientLogsRequestJson.getInstance(), PostClientLogsResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mClientOzExtension = EsAnalyticsData.createClientOzExtension(paramContext);
  }

  public final List<ClientOzEvent> getClientOzEvents()
  {
    if (this.mClientOzExtension == null);
    for (Object localObject = null; ; localObject = this.mClientOzExtension.clientEvent)
      return localObject;
  }

  public final void setClientOzEvents(List<ClientOzEvent> paramList)
  {
    this.mClientOzExtension.clientEvent = paramList;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.PostClientLogsOperation
 * JD-Core Version:    0.6.2
 */