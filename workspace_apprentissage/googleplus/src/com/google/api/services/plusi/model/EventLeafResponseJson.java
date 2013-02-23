package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventLeafResponseJson extends EsJson<EventLeafResponse>
{
  static final EventLeafResponseJson INSTANCE = new EventLeafResponseJson();

  private EventLeafResponseJson()
  {
    super(EventLeafResponse.class, new Object[] { "activityId", TraceRecordsJson.class, "backendTrace", CommentJson.class, "comments", EventFrameJson.class, "frames", ReadResponsePhotosDataJson.class, "photosData", PlusEventJson.class, "plusEvent", "pollingToken", "resumeToken", "selectedCriteria", "state", "status", StreamJson.class, "stream", UpdateJson.class, "update" });
  }

  public static EventLeafResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventLeafResponseJson
 * JD-Core Version:    0.6.2
 */