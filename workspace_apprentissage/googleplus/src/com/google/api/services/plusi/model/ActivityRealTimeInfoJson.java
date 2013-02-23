package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActivityRealTimeInfoJson extends EsJson<ActivityRealTimeInfo>
{
  static final ActivityRealTimeInfoJson INSTANCE = new ActivityRealTimeInfoJson();

  private ActivityRealTimeInfoJson()
  {
    super(ActivityRealTimeInfo.class, new Object[] { "minPollIntervalSec" });
  }

  public static ActivityRealTimeInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityRealTimeInfoJson
 * JD-Core Version:    0.6.2
 */