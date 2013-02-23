package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySegmentedShareDataShareDataJson extends EsJson<EntitySegmentedShareDataShareData>
{
  static final EntitySegmentedShareDataShareDataJson INSTANCE = new EntitySegmentedShareDataShareDataJson();

  private EntitySegmentedShareDataShareDataJson()
  {
    super(EntitySegmentedShareDataShareData.class, new Object[] { "activityId", "authorOid", "entityType", "permalink" });
  }

  public static EntitySegmentedShareDataShareDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySegmentedShareDataShareDataJson
 * JD-Core Version:    0.6.2
 */