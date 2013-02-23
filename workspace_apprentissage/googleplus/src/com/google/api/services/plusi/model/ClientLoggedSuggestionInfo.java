package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;
import java.util.List;

public final class ClientLoggedSuggestionInfo extends GenericJson
{
  public Integer celebrityCategoryId;
  public BigInteger deprecatedFriendSuggestionSummarizedInfoBitmask;
  public Integer explanationType;
  public Integer explanationsTypesBitmask;
  public Integer friendSuggestionSummarizedAdditionalInfoBitmask;
  public Integer friendSuggestionSummarizedInfoBitmask;
  public Integer numberOfCircleMembersAdded;
  public Integer numberOfCircleMembersRemoved;
  public Integer placement;
  public Double score;
  public String scoringExperiments;
  public List<ClientLoggedCircleMember> seed;
  public ClientLoggedCircle suggestedCircle;
  public List<ClientLoggedCircleMember> suggestedCircleMember;
  public String suggestionId;
  public Integer suggestionType;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedSuggestionInfo
 * JD-Core Version:    0.6.2
 */