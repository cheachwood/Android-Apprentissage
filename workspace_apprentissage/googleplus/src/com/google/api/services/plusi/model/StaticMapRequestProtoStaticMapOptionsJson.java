package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StaticMapRequestProtoStaticMapOptionsJson extends EsJson<StaticMapRequestProtoStaticMapOptions>
{
  static final StaticMapRequestProtoStaticMapOptionsJson INSTANCE = new StaticMapRequestProtoStaticMapOptionsJson();

  private StaticMapRequestProtoStaticMapOptionsJson()
  {
    super(StaticMapRequestProtoStaticMapOptions.class, new Object[] { "height", "highDpi", "width", "zoomLevel" });
  }

  public static StaticMapRequestProtoStaticMapOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StaticMapRequestProtoStaticMapOptionsJson
 * JD-Core Version:    0.6.2
 */