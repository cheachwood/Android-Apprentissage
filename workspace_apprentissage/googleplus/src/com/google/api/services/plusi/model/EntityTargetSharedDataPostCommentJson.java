package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityTargetSharedDataPostCommentJson extends EsJson<EntityTargetSharedDataPostComment>
{
  static final EntityTargetSharedDataPostCommentJson INSTANCE = new EntityTargetSharedDataPostCommentJson();

  private EntityTargetSharedDataPostCommentJson()
  {
    super(EntityTargetSharedDataPostComment.class, new Object[] { "commenterOid", "id" });
  }

  public static EntityTargetSharedDataPostCommentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityTargetSharedDataPostCommentJson
 * JD-Core Version:    0.6.2
 */