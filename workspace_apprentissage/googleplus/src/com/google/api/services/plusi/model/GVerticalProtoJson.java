package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GVerticalProtoJson extends EsJson<GVerticalProto>
{
  static final GVerticalProtoJson INSTANCE = new GVerticalProtoJson();

  private GVerticalProtoJson()
  {
    super(GVerticalProto.class, new Object[] { "gvertical" });
  }

  public static GVerticalProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GVerticalProtoJson
 * JD-Core Version:    0.6.2
 */