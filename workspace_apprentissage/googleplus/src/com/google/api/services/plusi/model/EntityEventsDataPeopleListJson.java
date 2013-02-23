package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityEventsDataPeopleListJson extends EsJson<EntityEventsDataPeopleList>
{
  static final EntityEventsDataPeopleListJson INSTANCE = new EntityEventsDataPeopleListJson();

  private EntityEventsDataPeopleListJson()
  {
    super(EntityEventsDataPeopleList.class, new Object[] { EmbedsPersonJson.class, "person", "totalPeople" });
  }

  public static EntityEventsDataPeopleListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEventsDataPeopleListJson
 * JD-Core Version:    0.6.2
 */