package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityHangoutDataJson extends EsJson<EntityHangoutData>
{
  static final EntityHangoutDataJson INSTANCE = new EntityHangoutDataJson();

  private EntityHangoutDataJson()
  {
    super(EntityHangoutData.class, new Object[] { A2aDataJson.class, "a2aData", UpdateJson.class, "update" });
  }

  public static EntityHangoutDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityHangoutDataJson
 * JD-Core Version:    0.6.2
 */