package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NotificationsResponseOptionsJson extends EsJson<NotificationsResponseOptions>
{
  static final NotificationsResponseOptionsJson INSTANCE = new NotificationsResponseOptionsJson();

  private NotificationsResponseOptionsJson()
  {
    super(NotificationsResponseOptions.class, new Object[] { "includeActorMap", "includeDeletedEntities", "includeFullActorDetails", "includeFullEntityDetails", "includeFullRootDetails", "numPhotoEntities" });
  }

  public static NotificationsResponseOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NotificationsResponseOptionsJson
 * JD-Core Version:    0.6.2
 */