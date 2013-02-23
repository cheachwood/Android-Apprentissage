package com.google.android.apps.plus.api;

import android.content.Context;
import android.content.Intent;
import com.google.android.apps.plus.content.EsAccount;
import com.google.android.apps.plus.network.HttpOperation.OperationListener;
import com.google.android.apps.plus.network.PlusiOperation;
import com.google.api.services.plusi.model.DataActor;
import com.google.api.services.plusi.model.DataCoalescedItem;
import com.google.api.services.plusi.model.GetNotificationsRequest;
import com.google.api.services.plusi.model.GetNotificationsRequestJson;
import com.google.api.services.plusi.model.GetNotificationsResponse;
import com.google.api.services.plusi.model.GetNotificationsResponseJson;
import java.util.Arrays;
import java.util.List;

public final class GetNotificationsOperation extends PlusiOperation<GetNotificationsRequest, GetNotificationsResponse>
{
  public static final List<String> TYPE_GROUP_TO_FETCH = Arrays.asList(new String[] { "BASELINE_STREAM", "BASELINE_CIRCLE", "BASELINE_PHOTOS", "BASELINE_EVENTS", "BASELINE_SQUARE", "CIRCLE_SUGGESTIONS_GROUP" });
  private List<DataActor> mActors;
  private String mContinuationToken;
  private double mLastNotificationTime;
  private Double mLastReadTime;
  private List<DataCoalescedItem> mNotifications;

  public GetNotificationsOperation(Context paramContext, EsAccount paramEsAccount, Intent paramIntent, HttpOperation.OperationListener paramOperationListener)
  {
    super(paramContext, paramEsAccount, "getnotifications", GetNotificationsRequestJson.getInstance(), GetNotificationsResponseJson.getInstance(), null, paramOperationListener);
  }

  public final String getContinuationToken()
  {
    return this.mContinuationToken;
  }

  public final List<DataActor> getDataActors()
  {
    return this.mActors;
  }

  public final Double getLastReadTime()
  {
    return this.mLastReadTime;
  }

  public final List<DataCoalescedItem> getNotifications()
  {
    return this.mNotifications;
  }

  public final void getNotifications(double paramDouble, String paramString)
  {
    this.mLastNotificationTime = paramDouble;
    this.mContinuationToken = paramString;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.android.apps.plus.api.GetNotificationsOperation
 * JD-Core Version:    0.6.2
 */