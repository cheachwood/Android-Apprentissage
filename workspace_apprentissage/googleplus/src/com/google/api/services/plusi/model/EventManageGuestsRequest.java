package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EventManageGuestsRequest extends GenericJson
{
  public String actionType;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public String eventId;
  public EventSelector eventSelector;
  public List<EmbedsPerson> invitee;
  public List<EmbedsSquare> square;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventManageGuestsRequest
 * JD-Core Version:    0.6.2
 */