package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateNotificationsLastReadTimeResponseJson extends EsJson<UpdateNotificationsLastReadTimeResponse>
{
  static final UpdateNotificationsLastReadTimeResponseJson INSTANCE = new UpdateNotificationsLastReadTimeResponseJson();

  private UpdateNotificationsLastReadTimeResponseJson()
  {
    super(UpdateNotificationsLastReadTimeResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "lastReadTime" });
  }

  public static UpdateNotificationsLastReadTimeResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateNotificationsLastReadTimeResponseJson
 * JD-Core Version:    0.6.2
 */