package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;
import java.util.List;

public final class LoggedRhsComponentItem extends GenericJson
{
  public List<LoggedCircle> circle;
  public Integer col;
  public List<Integer> connectSiteId;
  public List<BigInteger> gamesLabelId;
  public String localWriteReviewClusterId;
  public List<LoggedCircleMember> person;
  public Integer row;
  public List<LoggedSquare> square;
  public LoggedSuggestionInfo suggestionInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRhsComponentItem
 * JD-Core Version:    0.6.2
 */