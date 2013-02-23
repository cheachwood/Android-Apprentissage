package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EntityUpdateData extends GenericJson
{
  public Update activity;
  public List<String> commentId;
  public Comment contextComment;
  public EntityUpdateDataCountData countData;
  public String ownerOid;
  public String safeAnnotationHtml;
  public String safeTitleHtml;
  public EntityUpdateDataSummarySnippet summary;
  public Integer unreadCommentCount;
  public Integer unreadReshareCount;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityUpdateData
 * JD-Core Version:    0.6.2
 */