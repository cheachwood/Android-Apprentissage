package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutStartContextInviteeInviteeIdJson extends EsJson<HangoutStartContextInviteeInviteeId>
{
  static final HangoutStartContextInviteeInviteeIdJson INSTANCE = new HangoutStartContextInviteeInviteeIdJson();

  private HangoutStartContextInviteeInviteeIdJson()
  {
    super(HangoutStartContextInviteeInviteeId.class, new Object[] { "phoneNumber", "profileId" });
  }

  public static HangoutStartContextInviteeInviteeIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContextInviteeInviteeIdJson
 * JD-Core Version:    0.6.2
 */