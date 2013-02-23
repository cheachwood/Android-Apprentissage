package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class InviteeSummary extends GenericJson
{
  public Integer count;
  public List<Invitee> invitee;
  public String rsvpType;
  public Boolean setByViewer;
  public Integer viewerNumAdditionalGuests;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InviteeSummary
 * JD-Core Version:    0.6.2
 */