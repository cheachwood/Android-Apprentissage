package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActivityResultsJson extends EsJson<ActivityResults>
{
  static final ActivityResultsJson INSTANCE = new ActivityResultsJson();

  private ActivityResultsJson()
  {
    super(ActivityResults.class, new Object[] { "activityCount", ActivityRealTimeInfoJson.class, "realTimeInfo", "shownActivitiesBlob", StreamJson.class, "stream" });
  }

  public static ActivityResultsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityResultsJson
 * JD-Core Version:    0.6.2
 */