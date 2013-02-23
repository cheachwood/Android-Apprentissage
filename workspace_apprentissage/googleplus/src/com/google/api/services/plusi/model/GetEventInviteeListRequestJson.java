package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetEventInviteeListRequestJson extends EsJson<GetEventInviteeListRequest>
{
  static final GetEventInviteeListRequestJson INSTANCE = new GetEventInviteeListRequestJson();

  private GetEventInviteeListRequestJson()
  {
    super(GetEventInviteeListRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "eventId", EventSelectorJson.class, "eventSelector", "includeAdminBlacklist", "includeSquares", "invitationToken", "maxInvitees", "photoContributorsOnly", "resumeToken", "rsvpType", "taggeesOnly" });
  }

  public static GetEventInviteeListRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetEventInviteeListRequestJson
 * JD-Core Version:    0.6.2
 */