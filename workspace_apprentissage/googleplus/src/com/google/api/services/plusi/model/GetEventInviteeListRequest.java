package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetEventInviteeListRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public String eventId;
  public EventSelector eventSelector;
  public Boolean includeAdminBlacklist;
  public Boolean includeSquares;
  public String invitationToken;
  public Integer maxInvitees;
  public Boolean photoContributorsOnly;
  public String resumeToken;
  public String rsvpType;
  public Boolean taggeesOnly;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetEventInviteeListRequest
 * JD-Core Version:    0.6.2
 */