package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityEventsDataChangedFieldsJson extends EsJson<EntityEventsDataChangedFields>
{
  static final EntityEventsDataChangedFieldsJson INSTANCE = new EntityEventsDataChangedFieldsJson();

  private EntityEventsDataChangedFieldsJson()
  {
    super(EntityEventsDataChangedFields.class, new Object[] { "location", "startTime" });
  }

  public static EntityEventsDataChangedFieldsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityEventsDataChangedFieldsJson
 * JD-Core Version:    0.6.2
 */