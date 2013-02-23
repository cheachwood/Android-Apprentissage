package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityCirclesDataJson extends EsJson<EntityCirclesData>
{
  static final EntityCirclesDataJson INSTANCE = new EntityCirclesDataJson();

  private EntityCirclesDataJson()
  {
    super(EntityCirclesData.class, new Object[] { "lowQualityObfuscatedGaiaId", "totalLowQualityAdders" });
  }

  public static EntityCirclesDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityCirclesDataJson
 * JD-Core Version:    0.6.2
 */