package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class FindMorePeopleResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public String experimentNames;
  public Double experimentThreshold;
  public List<DataImportedPerson> imported;
  public String portraitVersion;
  public List<DataSuggestedPerson> promotedSuggestion;
  public String queryId;
  public List<DataSuggestedPerson> suggestion;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FindMorePeopleResponse
 * JD-Core Version:    0.6.2
 */