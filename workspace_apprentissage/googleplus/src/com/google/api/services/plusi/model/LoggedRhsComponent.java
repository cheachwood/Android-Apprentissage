package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LoggedRhsComponent extends GenericJson
{
  public Integer barType;
  public LoggedRhsComponentType componentType;
  public Integer index;
  public List<LoggedRhsComponentItem> item;
  public List<LoggedRhsComponentType> neighborInfo;
  public LoggedSuggestionSummaryInfo suggestionSummaryInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRhsComponent
 * JD-Core Version:    0.6.2
 */