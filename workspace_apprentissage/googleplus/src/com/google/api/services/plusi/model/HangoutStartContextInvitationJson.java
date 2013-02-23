package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutStartContextInvitationJson extends EsJson<HangoutStartContextInvitation>
{
  static final HangoutStartContextInvitationJson INSTANCE = new HangoutStartContextInvitationJson();

  private HangoutStartContextInvitationJson()
  {
    super(HangoutStartContextInvitation.class, arrayOfObject);
  }

  public static HangoutStartContextInvitationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutStartContextInvitationJson
 * JD-Core Version:    0.6.2
 */