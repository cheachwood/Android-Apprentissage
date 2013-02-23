package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSuggestionActionJson extends EsJson<DataSuggestionAction>
{
  static final DataSuggestionActionJson INSTANCE = new DataSuggestionActionJson();

  private DataSuggestionActionJson()
  {
    super(DataSuggestionAction.class, new Object[] { "accepted", "actionType", DataCircleIdJson.class, "circleId", DataSuggestedEntityIdJson.class, "suggestedEntityId", "suggestionContext", DataCircleMemberIdJson.class, "suggestionId", "suggestionUi" });
  }

  public static DataSuggestionActionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestionActionJson
 * JD-Core Version:    0.6.2
 */