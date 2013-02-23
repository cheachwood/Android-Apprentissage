package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StarredItemProtoJson extends EsJson<StarredItemProto>
{
  static final StarredItemProtoJson INSTANCE = new StarredItemProtoJson();

  private StarredItemProtoJson()
  {
    super(StarredItemProto.class, new Object[] { "annotationUrl", "starred" });
  }

  public static StarredItemProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StarredItemProtoJson
 * JD-Core Version:    0.6.2
 */