package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Invitee extends GenericJson
{
  public List<PlusEventAlbum> album;
  public Boolean canUploadPhotos;
  public EventTime deprecated10;
  public EmbedsPerson invitee;
  public EmbedsPerson inviter;
  public EmbedsSquare inviterSquare;
  public Boolean isAdminBlacklisted;
  public Integer numAdditionalGuests;
  public String readState;
  public String rsvpToken;
  public String rsvpType;
  public EmbedsSquare square;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Invitee
 * JD-Core Version:    0.6.2
 */