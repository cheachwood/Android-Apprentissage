package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InviteEventRequestJson extends EsJson<InviteEventRequest>
{
  static final InviteEventRequestJson INSTANCE = new InviteEventRequestJson();

  private InviteEventRequestJson()
  {
    super(InviteEventRequest.class, new Object[] { "aclDelta", ApiaryFieldsJson.class, "commonFields", "enableTracing", "eventId", EventSelectorJson.class, "eventSelector", SharingRosterJson.class, "inviteRoster", "location", "organizerId" });
  }

  public static InviteEventRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InviteEventRequestJson
 * JD-Core Version:    0.6.2
 */