package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientLoggedRhsComponentItem extends GenericJson
{
  public List<ClientLoggedCircle> circle;
  public Integer col;
  public List<Integer> connectSiteId;
  public List<String> gamesLabelId;
  public ClientLoggedHangout hangout;
  public String localWriteReviewClusterId;
  public ClientLoggedLocalWriteReviewInfo localWriteReviewInfo;
  public List<ClientLoggedCircleMember> person;
  public Integer row;
  public List<ClientLoggedSquare> square;
  public ClientLoggedSuggestionInfo suggestionInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRhsComponentItem
 * JD-Core Version:    0.6.2
 */