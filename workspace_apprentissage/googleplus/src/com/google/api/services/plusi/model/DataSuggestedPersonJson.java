package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSuggestedPersonJson extends EsJson<DataSuggestedPerson>
{
  static final DataSuggestedPersonJson INSTANCE = new DataSuggestedPersonJson();

  private DataSuggestedPersonJson()
  {
    super(DataSuggestedPerson.class, new Object[] { "debugInfo", DataSugggestionExplanationJson.class, "explanation", "friendSuggestionSummarizedAdditionalInfoBitmask", "friendSuggestionSummarizedInfoBitmask", DataCirclePersonJson.class, "member", PaidPromotionSuggestedPersonInfoJson.class, "paidPromotionInfo", DataCircleIdJson.class, "relevantCircleId", "score", "shownCount", "suggestionId" });
  }

  public static DataSuggestedPersonJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestedPersonJson
 * JD-Core Version:    0.6.2
 */