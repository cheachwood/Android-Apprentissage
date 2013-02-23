package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GetEventInviteeListResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<Invitee> invitee;
  public String resumeToken;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetEventInviteeListResponse
 * JD-Core Version:    0.6.2
 */