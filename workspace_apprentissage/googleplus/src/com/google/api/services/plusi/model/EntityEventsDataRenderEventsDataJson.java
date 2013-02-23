package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityEventsDataRenderEventsDataJson extends EsJson<EntityEventsDataRenderEventsData>
{
  static final EntityEventsDataRenderEventsDataJson INSTANCE = new EntityEventsDataRenderEventsDataJson();

  private EntityEventsDataRenderEventsDataJson()
  {
    super(EntityEventsDataRenderEventsData.class, new Object[] { "displayType", EventActionUserListJson.class, "eventActionUserList", "isEventCreator", "numNewUpdates" });
  }

  public static EntityEventsDataRenderEventsDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEventsDataRenderEventsDataJson
 * JD-Core Version:    0.6.2
 */