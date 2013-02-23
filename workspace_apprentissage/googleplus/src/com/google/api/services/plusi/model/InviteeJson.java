package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InviteeJson extends EsJson<Invitee>
{
  static final InviteeJson INSTANCE = new InviteeJson();

  private InviteeJson()
  {
    super(Invitee.class, new Object[] { PlusEventAlbumJson.class, "album", "canUploadPhotos", EventTimeJson.class, "deprecated10", EmbedsPersonJson.class, "invitee", EmbedsPersonJson.class, "inviter", EmbedsSquareJson.class, "inviterSquare", "isAdminBlacklisted", "numAdditionalGuests", "readState", "rsvpToken", "rsvpType", EmbedsSquareJson.class, "square" });
  }

  public static InviteeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InviteeJson
 * JD-Core Version:    0.6.2
 */