package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PromotedYMLImpressionJson extends EsJson<PromotedYMLImpression>
{
  static final PromotedYMLImpressionJson INSTANCE = new PromotedYMLImpressionJson();

  private PromotedYMLImpressionJson()
  {
    super(PromotedYMLImpression.class, new Object[] { PromotedYMLImpressionItemJson.class, "ymlImpressionItem" });
  }

  public static PromotedYMLImpressionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PromotedYMLImpressionJson
 * JD-Core Version:    0.6.2
 */