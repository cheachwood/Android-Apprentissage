package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventLeafResponse extends GenericJson
{
  public String activityId;
  public TraceRecords backendTrace;
  public List<Comment> comments;
  public List<EventFrame> frames;
  public List<ReadResponsePhotosData> photosData;
  public PlusEvent plusEvent;
  public String pollingToken;
  public String resumeToken;
  public String selectedCriteria;
  public String state;
  public String status;
  public Stream stream;
  public Update update;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventLeafResponse
 * JD-Core Version:    0.6.2
 */