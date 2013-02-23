package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateNotificationsLastReadTimeRequestJson extends EsJson<UpdateNotificationsLastReadTimeRequest>
{
  static final UpdateNotificationsLastReadTimeRequestJson INSTANCE = new UpdateNotificationsLastReadTimeRequestJson();

  private UpdateNotificationsLastReadTimeRequestJson()
  {
    super(UpdateNotificationsLastReadTimeRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "timeMs" });
  }

  public static UpdateNotificationsLastReadTimeRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeRequestJson
 * JD-Core Version:    0.6.2
 */