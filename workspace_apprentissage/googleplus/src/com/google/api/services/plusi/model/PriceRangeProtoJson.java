package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PriceRangeProtoJson extends EsJson<PriceRangeProto>
{
  static final PriceRangeProtoJson INSTANCE = new PriceRangeProtoJson();

  private PriceRangeProtoJson()
  {
    super(PriceRangeProto.class, new Object[] { "currency", "lowerPrice", "units", "upperPrice" });
  }

  public static PriceRangeProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PriceRangeProtoJson
 * JD-Core Version:    0.6.2
 */