package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityLegacyClientDataJson extends EsJson<EntityLegacyClientData>
{
  static final EntityLegacyClientDataJson INSTANCE = new EntityLegacyClientDataJson();

  private EntityLegacyClientDataJson()
  {
    super(EntityLegacyClientData.class, new Object[] { EntityLegacyClientDataLegacyKeyValueJson.class, "legacyClientField" });
  }

  public static EntityLegacyClientDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityLegacyClientDataJson
 * JD-Core Version:    0.6.2
 */