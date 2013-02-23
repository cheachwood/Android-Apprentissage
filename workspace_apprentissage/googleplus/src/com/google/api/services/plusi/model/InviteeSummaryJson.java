package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InviteeSummaryJson extends EsJson<InviteeSummary>
{
  static final InviteeSummaryJson INSTANCE = new InviteeSummaryJson();

  private InviteeSummaryJson()
  {
    super(InviteeSummary.class, new Object[] { "count", InviteeJson.class, "invitee", "rsvpType", "setByViewer", "viewerNumAdditionalGuests" });
  }

  public static InviteeSummaryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InviteeSummaryJson
 * JD-Core Version:    0.6.2
 */