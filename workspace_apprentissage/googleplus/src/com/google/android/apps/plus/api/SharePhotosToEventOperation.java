package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.SharePhotosToEventRequest;
import com.google.api.services.plusi.model.SharePhotosToEventRequestJson;
import com.google.api.services.plusi.model.SharePhotosToEventResponse;
import com.google.api.services.plusi.model.SharePhotosToEventResponseJson;
import java.util.List;

public final class SharePhotosToEventOperation extends PlusiOperation<SharePhotosToEventRequest, SharePhotosToEventResponse>
{
  final String mEventId;
  final List<Long> mPhotoIds;

  public SharePhotosToEventOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, List<Long> paramList, String paramString, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "sharephotostoevent", SharePhotosToEventRequestJson.getInstance(), SharePhotosToEventResponseJson.getInstance(), paramIntent, paramOperationListener);
    this.mEventId = paramString;
    this.mPhotoIds = paramList;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.SharePhotosToEventOperation
 * JD-Core Version:    0.6.2
 */