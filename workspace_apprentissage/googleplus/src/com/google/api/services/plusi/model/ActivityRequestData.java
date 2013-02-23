package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class ActivityRequestData extends GenericJson
{
  public Boolean activityCountOnly;
  public ActivityFilters activityFilters;
  public FieldRequestOptions fieldRequestOptions;
  public Integer maxResults;
  public String shownActivitiesBlob;
  public UpdateFilter updateFilter;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityRequestData
 * JD-Core Version:    0.6.2
 */