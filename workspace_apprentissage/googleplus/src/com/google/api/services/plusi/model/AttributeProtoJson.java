package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AttributeProtoJson extends EsJson<AttributeProto>
{
  static final AttributeProtoJson INSTANCE = new AttributeProtoJson();

  private AttributeProtoJson()
  {
    super(AttributeProto.class, new Object[] { PlacePageLinkJson.class, "attribution", "labelAttribution", "labelDisplay", "labelId", AttributeProtoCanonicalValueJson.class, "value", "valueAttribution", "valueDisplay", "valueDisplaySuffix", "valueSpaceId", "ved" });
  }

  public static AttributeProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AttributeProtoJson
 * JD-Core Version:    0.6.2
 */