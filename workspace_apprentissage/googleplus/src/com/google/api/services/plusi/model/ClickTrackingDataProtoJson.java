package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClickTrackingDataProtoJson extends EsJson<ClickTrackingDataProto>
{
  static final ClickTrackingDataProtoJson INSTANCE = new ClickTrackingDataProtoJson();

  private ClickTrackingDataProtoJson()
  {
    super(ClickTrackingDataProto.class, new Object[] { "additionalData", ClickTrackingCGIJson.class, "clickTrackingCgi", "geocode", "originalUrl", "provider" });
  }

  public static ClickTrackingDataProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClickTrackingDataProtoJson
 * JD-Core Version:    0.6.2
 */