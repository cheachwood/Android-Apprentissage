package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class EventsHomeRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Integer maxDeclinedUpcomingEvents;
  public Integer maxPastEvents;
  public Integer maxUpcomingEvents;
  public String pastResumeToken;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventsHomeRequest
 * JD-Core Version:    0.6.2
 */