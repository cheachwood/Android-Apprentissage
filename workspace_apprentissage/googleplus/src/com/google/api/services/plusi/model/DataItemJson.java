package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataItemJson extends EsJson<DataItem>
{
  static final DataItemJson INSTANCE = new DataItemJson();

  private DataItemJson()
  {
    super(DataItem.class, new Object[] { DataActorJson.class, "actor", "actorOid", "id", "isRead", "notificationType", DataKvPairJson.class, "opaqueClientFields", "timestamp" });
  }

  public static DataItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataItemJson
 * JD-Core Version:    0.6.2
 */