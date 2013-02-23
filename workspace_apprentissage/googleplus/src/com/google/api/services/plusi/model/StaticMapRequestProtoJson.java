package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StaticMapRequestProtoJson extends EsJson<StaticMapRequestProto>
{
  static final StaticMapRequestProtoJson INSTANCE = new StaticMapRequestProtoJson();

  private StaticMapRequestProtoJson()
  {
    super(StaticMapRequestProto.class, new Object[] { "showCopyrightMessage", StaticMapRequestProtoStaticMapOptionsJson.class, "staticMapOptions" });
  }

  public static StaticMapRequestProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StaticMapRequestProtoJson
 * JD-Core Version:    0.6.2
 */