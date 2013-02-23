package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutStartContextInviteeJson extends EsJson<HangoutStartContextInvitee>
{
  static final HangoutStartContextInviteeJson INSTANCE = new HangoutStartContextInviteeJson();

  private HangoutStartContextInviteeJson()
  {
    super(HangoutStartContextInvitee.class, new Object[] { HangoutStartContextInviteeInviteeIdJson.class, "inviteeId", "profileId", "profileName" });
  }

  public static HangoutStartContextInviteeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContextInviteeJson
 * JD-Core Version:    0.6.2
 */