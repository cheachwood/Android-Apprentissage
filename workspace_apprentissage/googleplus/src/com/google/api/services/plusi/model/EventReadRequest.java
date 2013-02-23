package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventReadRequest extends GenericJson
{
  public String authToken;
  public ApiaryFields commonFields;
  public String contentFormat;
  public Boolean enableTracing;
  public EventSelector eventSelector;
  public String invitationToken;
  public String pollingToken;
  public List<ReadOptions> readOptions;
  public Boolean requestAnonymously;
  public String resumeToken;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventReadRequest
 * JD-Core Version:    0.6.2
 */