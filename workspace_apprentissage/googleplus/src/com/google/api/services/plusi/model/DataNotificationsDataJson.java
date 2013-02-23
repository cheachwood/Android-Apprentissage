package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataNotificationsDataJson extends EsJson<DataNotificationsData>
{
  static final DataNotificationsDataJson INSTANCE = new DataNotificationsDataJson();

  private DataNotificationsDataJson()
  {
    super(DataNotificationsData.class, new Object[] { DataActorJson.class, "actor", DataCoalescedItemJson.class, "coalescedItem", "continuationToken", DataCoalescedItemJson.class, "featuredItem", "filteredId", "hasMoreNotifications", "hasMoreUnreadNotifications", "lastReadTime", "latestNotificationTime", "unreadCount", DataUnreadCountDataJson.class, "unreadCountData" });
  }

  public static DataNotificationsDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationsDataJson
 * JD-Core Version:    0.6.2
 */