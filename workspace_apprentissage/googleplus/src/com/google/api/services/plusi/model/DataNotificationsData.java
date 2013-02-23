package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataNotificationsData extends GenericJson
{
  public List<DataActor> actor;
  public List<DataCoalescedItem> coalescedItem;
  public String continuationToken;
  public DataCoalescedItem featuredItem;
  public List<String> filteredId;
  public Boolean hasMoreNotifications;
  public Boolean hasMoreUnreadNotifications;
  public Double lastReadTime;
  public Double latestNotificationTime;
  public Integer unreadCount;
  public DataUnreadCountData unreadCountData;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationsData
 * JD-Core Version:    0.6.2
 */