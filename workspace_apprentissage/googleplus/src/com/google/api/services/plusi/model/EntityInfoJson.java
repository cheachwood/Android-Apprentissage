package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityInfoJson extends EsJson<EntityInfo>
{
  static final EntityInfoJson INSTANCE = new EntityInfoJson();

  private EntityInfoJson()
  {
    super(EntityInfo.class, new Object[] { LocalEntityInfoJson.class, "localInfo", ProfilesLinkJson.class, "primaryLink", "type" });
  }

  public static EntityInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityInfoJson
 * JD-Core Version:    0.6.2
 */