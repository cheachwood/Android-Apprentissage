package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StaticMapProtoJson extends EsJson<StaticMapProto>
{
  static final StaticMapProtoJson INSTANCE = new StaticMapProtoJson();

  private StaticMapProtoJson()
  {
    super(StaticMapProto.class, arrayOfObject);
  }

  public static StaticMapProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StaticMapProtoJson
 * JD-Core Version:    0.6.2
 */