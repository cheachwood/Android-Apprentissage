package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityBirthdayDataJson extends EsJson<EntityBirthdayData>
{
  static final EntityBirthdayDataJson INSTANCE = new EntityBirthdayDataJson();

  private EntityBirthdayDataJson()
  {
    super(EntityBirthdayData.class, arrayOfObject);
  }

  public static EntityBirthdayDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityBirthdayDataJson
 * JD-Core Version:    0.6.2
 */