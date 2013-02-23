package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PriceLevelProtoJson extends EsJson<PriceLevelProto>
{
  static final PriceLevelProtoJson INSTANCE = new PriceLevelProtoJson();

  private PriceLevelProtoJson()
  {
    super(PriceLevelProto.class, new Object[] { "labelDisplay", "labelHintDisplay", "valueId" });
  }

  public static PriceLevelProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PriceLevelProtoJson
 * JD-Core Version:    0.6.2
 */