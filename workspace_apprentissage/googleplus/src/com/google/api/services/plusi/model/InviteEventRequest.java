package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class InviteEventRequest extends GenericJson
{
  public String aclDelta;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public String eventId;
  public EventSelector eventSelector;
  public SharingRoster inviteRoster;
  public String location;
  public String organizerId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InviteEventRequest
 * JD-Core Version:    0.6.2
 */