package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActivityFiltersJson extends EsJson<ActivityFilters>
{
  static final ActivityFiltersJson INSTANCE = new ActivityFiltersJson();

  private ActivityFiltersJson()
  {
    super(ActivityFilters.class, new Object[] { FieldRequestOptionsJson.class, "fieldRequestOptions", "maxNumImages", "skipCommentCollapsing", UpdateFilterJson.class, "updateFilter" });
  }

  public static ActivityFiltersJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityFiltersJson
 * JD-Core Version:    0.6.2
 */