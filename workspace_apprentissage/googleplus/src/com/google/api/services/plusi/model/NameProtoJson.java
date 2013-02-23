package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NameProtoJson extends EsJson<NameProto>
{
  static final NameProtoJson INSTANCE = new NameProtoJson();

  private NameProtoJson()
  {
    super(NameProto.class, new Object[] { "flag", "language", "rawText", "routeDirection", "shortText", "text" });
  }

  public static NameProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NameProtoJson
 * JD-Core Version:    0.6.2
 */