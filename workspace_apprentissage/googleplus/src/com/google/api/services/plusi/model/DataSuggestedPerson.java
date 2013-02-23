package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataSuggestedPerson extends GenericJson
{
  public List<String> debugInfo;
  public DataSugggestionExplanation explanation;
  public Integer friendSuggestionSummarizedAdditionalInfoBitmask;
  public Integer friendSuggestionSummarizedInfoBitmask;
  public DataCirclePerson member;
  public PaidPromotionSuggestedPersonInfo paidPromotionInfo;
  public DataCircleId relevantCircleId;
  public Double score;
  public Integer shownCount;
  public String suggestionId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestedPerson
 * JD-Core Version:    0.6.2
 */