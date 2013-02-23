package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityUpdateDataJson extends EsJson<EntityUpdateData>
{
  static final EntityUpdateDataJson INSTANCE = new EntityUpdateDataJson();

  private EntityUpdateDataJson()
  {
    super(EntityUpdateData.class, new Object[] { UpdateJson.class, "activity", "commentId", CommentJson.class, "contextComment", EntityUpdateDataCountDataJson.class, "countData", "ownerOid", "safeAnnotationHtml", "safeTitleHtml", EntityUpdateDataSummarySnippetJson.class, "summary", "unreadCommentCount", "unreadReshareCount" });
  }

  public static EntityUpdateDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityUpdateDataJson
 * JD-Core Version:    0.6.2
 */