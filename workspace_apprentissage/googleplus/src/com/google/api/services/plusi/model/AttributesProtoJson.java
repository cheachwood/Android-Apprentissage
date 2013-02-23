package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AttributesProtoJson extends EsJson<AttributesProto>
{
  static final AttributesProtoJson INSTANCE = new AttributesProtoJson();

  private AttributesProtoJson()
  {
    super(AttributesProto.class, new Object[] { AttributeProtoJson.class, "attribute" });
  }

  public static AttributesProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AttributesProtoJson
 * JD-Core Version:    0.6.2
 */