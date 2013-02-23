package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityEventsDataJson extends EsJson<EntityEventsData>
{
  static final EntityEventsDataJson INSTANCE = new EntityEventsDataJson();

  private EntityEventsDataJson()
  {
    super(EntityEventsData.class, new Object[] { EntityEventsDataChangedFieldsJson.class, "changedFields", PlusEventJson.class, "deletedPlusEvent", "eventActivityId", InviteeSummaryJson.class, "inviteeSummary", EntityEventsDataPeopleListJson.class, "photoUploaders", PlusPhotoAlbumJson.class, "plusPhotoAlbum", EntityEventsDataRenderEventsDataJson.class, "renderEventsData" });
  }

  public static EntityEventsDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEventsDataJson
 * JD-Core Version:    0.6.2
 */