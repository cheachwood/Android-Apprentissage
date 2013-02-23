package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventsHomeResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<PlusEvent> declinedUpcoming;
  public List<PlusEvent> deprecated1;
  public List<PlusEvent> past;
  public String pastResumeToken;
  public List<EmbedsPerson> resolvedPerson;
  public List<PlusEvent> upcoming;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventsHomeResponse
 * JD-Core Version:    0.6.2
 */