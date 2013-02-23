package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataSuggestionAction extends GenericJson
{
  public Boolean accepted;
  public String actionType;
  public DataCircleId circleId;
  public List<DataSuggestedEntityId> suggestedEntityId;
  public String suggestionContext;
  public List<DataCircleMemberId> suggestionId;
  public String suggestionUi;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestionAction
 * JD-Core Version:    0.6.2
 */