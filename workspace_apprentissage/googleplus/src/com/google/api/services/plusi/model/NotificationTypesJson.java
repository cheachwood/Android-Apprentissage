package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NotificationTypesJson extends EsJson<NotificationTypes>
{
  static final NotificationTypesJson INSTANCE = new NotificationTypesJson();

  private NotificationTypesJson()
  {
    super(NotificationTypes.class, new Object[] { "coalescingCode", "type" });
  }

  public static NotificationTypesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NotificationTypesJson
 * JD-Core Version:    0.6.2
 */