package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PriceProtoJson extends EsJson<PriceProto>
{
  static final PriceProtoJson INSTANCE = new PriceProtoJson();

  private PriceProtoJson()
  {
    super(PriceProto.class, new Object[] { "currency", "currencyCode", "labelDisplay", "labelHintDisplay", "valueDisplay" });
  }

  public static PriceProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PriceProtoJson
 * JD-Core Version:    0.6.2
 */