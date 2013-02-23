package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProductionStreamParamsJson extends EsJson<ProductionStreamParams>
{
  static final ProductionStreamParamsJson INSTANCE = new ProductionStreamParamsJson();

  private ProductionStreamParamsJson()
  {
    super(ProductionStreamParams.class, new Object[] { "type", "value" });
  }

  public static ProductionStreamParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProductionStreamParamsJson
 * JD-Core Version:    0.6.2
 */