package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PromotedYMLImpressionItemJson extends EsJson<PromotedYMLImpressionItem>
{
  static final PromotedYMLImpressionItemJson INSTANCE = new PromotedYMLImpressionItemJson();

  private PromotedYMLImpressionItemJson()
  {
    super(PromotedYMLImpressionItem.class, arrayOfObject);
  }

  public static PromotedYMLImpressionItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PromotedYMLImpressionItemJson
 * JD-Core Version:    0.6.2
 */