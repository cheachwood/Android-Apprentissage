package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OwnerResponseProtoJson extends EsJson<OwnerResponseProto>
{
  static final OwnerResponseProtoJson INSTANCE = new OwnerResponseProtoJson();

  private OwnerResponseProtoJson()
  {
    super(OwnerResponseProto.class, new Object[] { "comment", "publishDate", TimeProtoJson.class, "time" });
  }

  public static OwnerResponseProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OwnerResponseProtoJson
 * JD-Core Version:    0.6.2
 */