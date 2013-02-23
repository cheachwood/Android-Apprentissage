package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PriceRangeFieldJson extends EsJson<PriceRangeField>
{
  static final PriceRangeFieldJson INSTANCE = new PriceRangeFieldJson();

  private PriceRangeFieldJson()
  {
    super(PriceRangeField.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static PriceRangeFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PriceRangeFieldJson
 * JD-Core Version:    0.6.2
 */