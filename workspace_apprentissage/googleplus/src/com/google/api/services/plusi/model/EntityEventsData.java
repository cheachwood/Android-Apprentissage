package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EntityEventsData extends GenericJson
{
  public EntityEventsDataChangedFields changedFields;
  public PlusEvent deletedPlusEvent;
  public String eventActivityId;
  public List<InviteeSummary> inviteeSummary;
  public EntityEventsDataPeopleList photoUploaders;
  public PlusPhotoAlbum plusPhotoAlbum;
  public EntityEventsDataRenderEventsData renderEventsData;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEventsData
 * JD-Core Version:    0.6.2
 */