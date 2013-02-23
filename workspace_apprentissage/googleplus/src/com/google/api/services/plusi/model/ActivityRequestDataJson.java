package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActivityRequestDataJson extends EsJson<ActivityRequestData>
{
  static final ActivityRequestDataJson INSTANCE = new ActivityRequestDataJson();

  private ActivityRequestDataJson()
  {
    super(ActivityRequestData.class, new Object[] { "activityCountOnly", ActivityFiltersJson.class, "activityFilters", FieldRequestOptionsJson.class, "fieldRequestOptions", "maxResults", "shownActivitiesBlob", UpdateFilterJson.class, "updateFilter" });
  }

  public static ActivityRequestDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityRequestDataJson
 * JD-Core Version:    0.6.2
 */