package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityTargetSharedDataPostJson extends EsJson<EntityTargetSharedDataPost>
{
  static final EntityTargetSharedDataPostJson INSTANCE = new EntityTargetSharedDataPostJson();

  private EntityTargetSharedDataPostJson()
  {
    super(EntityTargetSharedDataPost.class, new Object[] { "activityId", EntityTargetSharedDataPostCommentJson.class, "comment", "creatorOid" });
  }

  public static EntityTargetSharedDataPostJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityTargetSharedDataPostJson
 * JD-Core Version:    0.6.2
 */