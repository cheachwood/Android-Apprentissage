package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityLegacyClientDataLegacyKeyValueJson extends EsJson<EntityLegacyClientDataLegacyKeyValue>
{
  static final EntityLegacyClientDataLegacyKeyValueJson INSTANCE = new EntityLegacyClientDataLegacyKeyValueJson();

  private EntityLegacyClientDataLegacyKeyValueJson()
  {
    super(EntityLegacyClientDataLegacyKeyValue.class, new Object[] { "key", "value" });
  }

  public static EntityLegacyClientDataLegacyKeyValueJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityLegacyClientDataLegacyKeyValueJson
 * JD-Core Version:    0.6.2
 */