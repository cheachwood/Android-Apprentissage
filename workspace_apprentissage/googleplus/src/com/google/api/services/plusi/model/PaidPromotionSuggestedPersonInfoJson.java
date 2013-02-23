package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PaidPromotionSuggestedPersonInfoJson extends EsJson<PaidPromotionSuggestedPersonInfo>
{
  static final PaidPromotionSuggestedPersonInfoJson INSTANCE = new PaidPromotionSuggestedPersonInfoJson();

  private PaidPromotionSuggestedPersonInfoJson()
  {
    super(PaidPromotionSuggestedPersonInfo.class, arrayOfObject);
  }

  public static PaidPromotionSuggestedPersonInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PaidPromotionSuggestedPersonInfoJson
 * JD-Core Version:    0.6.2
 */