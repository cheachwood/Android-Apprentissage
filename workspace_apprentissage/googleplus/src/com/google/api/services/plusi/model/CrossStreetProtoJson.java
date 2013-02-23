package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CrossStreetProtoJson extends EsJson<CrossStreetProto>
{
  static final CrossStreetProtoJson INSTANCE = new CrossStreetProtoJson();

  private CrossStreetProtoJson()
  {
    super(CrossStreetProto.class, new Object[] { "metersAway", "name" });
  }

  public static CrossStreetProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CrossStreetProtoJson
 * JD-Core Version:    0.6.2
 */