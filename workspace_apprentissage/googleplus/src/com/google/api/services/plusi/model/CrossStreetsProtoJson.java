package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CrossStreetsProtoJson extends EsJson<CrossStreetsProto>
{
  static final CrossStreetsProtoJson INSTANCE = new CrossStreetsProtoJson();

  private CrossStreetsProtoJson()
  {
    super(CrossStreetsProto.class, new Object[] { CrossStreetProtoJson.class, "begin", CrossStreetProtoJson.class, "end", "formattedCrossStreets" });
  }

  public static CrossStreetsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CrossStreetsProtoJson
 * JD-Core Version:    0.6.2
 */