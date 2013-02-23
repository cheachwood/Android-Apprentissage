package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;
import java.util.List;

public final class GetNotificationsRequest extends GenericJson
{
  public BigInteger clientVersion;
  public ApiaryFields commonFields;
  public String continuationToken;
  public List<String> dontCoalesce;
  public Boolean enableCoalescing;
  public Boolean enableTracing;
  public String featuredNotificationId;
  public Boolean fetchUnreadCount;
  public String filter;
  public String geographicLocation;
  public Boolean loadViewerData;
  public Long maxResults;
  public List<String> notificationId;
  public NotificationsResponseOptions notificationsResponseOptions;
  public BigInteger oldestNotificationTimeUsec;
  public String renderContextLocation;
  public Boolean setPushEnabled;
  public List<String> summarySnippets;
  public List<String> typeGroupToFetch;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetNotificationsRequest
 * JD-Core Version:    0.6.2
 */