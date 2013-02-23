package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityTargetSharedDataJson extends EsJson<EntityTargetSharedData>
{
  static final EntityTargetSharedDataJson INSTANCE = new EntityTargetSharedDataJson();

  private EntityTargetSharedDataJson()
  {
    super(EntityTargetSharedData.class, new Object[] { "actorOid", EntityTargetSharedDataPostJson.class, "post", "targetId", "titleSanitizedHtml" });
  }

  public static EntityTargetSharedDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityTargetSharedDataJson
 * JD-Core Version:    0.6.2
 */