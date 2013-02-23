package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySegmentedShareDataJson extends EsJson<EntitySegmentedShareData>
{
  static final EntitySegmentedShareDataJson INSTANCE = new EntitySegmentedShareDataJson();

  private EntitySegmentedShareDataJson()
  {
    super(EntitySegmentedShareData.class, new Object[] { EntitySegmentedShareDataShareDataJson.class, "shareData" });
  }

  public static EntitySegmentedShareDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySegmentedShareDataJson
 * JD-Core Version:    0.6.2
 */