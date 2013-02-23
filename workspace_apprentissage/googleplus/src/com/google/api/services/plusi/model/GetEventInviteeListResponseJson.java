package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetEventInviteeListResponseJson extends EsJson<GetEventInviteeListResponse>
{
  static final GetEventInviteeListResponseJson INSTANCE = new GetEventInviteeListResponseJson();

  private GetEventInviteeListResponseJson()
  {
    super(GetEventInviteeListResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", InviteeJson.class, "invitee", "resumeToken" });
  }

  public static GetEventInviteeListResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetEventInviteeListResponseJson
 * JD-Core Version:    0.6.2
 */