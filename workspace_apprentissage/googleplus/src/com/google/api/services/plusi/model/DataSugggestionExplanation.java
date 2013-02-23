package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataSugggestionExplanation extends GenericJson
{
  public Integer activitiesByFriends;
  public List<DataCircleMemberId> commonFriend;
  public Boolean isFirstHop;
  public Boolean isSecondHop;
  public Integer numberOfCommonFriends;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSugggestionExplanation
 * JD-Core Version:    0.6.2
 */