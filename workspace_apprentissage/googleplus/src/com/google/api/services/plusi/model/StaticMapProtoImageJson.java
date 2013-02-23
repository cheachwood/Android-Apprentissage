package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StaticMapProtoImageJson extends EsJson<StaticMapProtoImage>
{
  static final StaticMapProtoImageJson INSTANCE = new StaticMapProtoImageJson();

  private StaticMapProtoImageJson()
  {
    super(StaticMapProtoImage.class, new Object[] { "height", "isHighDpi", "src", "width" });
  }

  public static StaticMapProtoImageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StaticMapProtoImageJson
 * JD-Core Version:    0.6.2
 */