package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSugggestionExplanationJson extends EsJson<DataSugggestionExplanation>
{
  static final DataSugggestionExplanationJson INSTANCE = new DataSugggestionExplanationJson();

  private DataSugggestionExplanationJson()
  {
    super(DataSugggestionExplanation.class, new Object[] { "activitiesByFriends", DataCircleMemberIdJson.class, "commonFriend", "isFirstHop", "isSecondHop", "numberOfCommonFriends" });
  }

  public static DataSugggestionExplanationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSugggestionExplanationJson
 * JD-Core Version:    0.6.2
 */