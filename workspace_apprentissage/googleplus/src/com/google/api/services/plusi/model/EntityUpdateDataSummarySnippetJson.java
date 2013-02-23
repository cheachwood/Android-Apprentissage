package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityUpdateDataSummarySnippetJson extends EsJson<EntityUpdateDataSummarySnippet>
{
  static final EntityUpdateDataSummarySnippetJson INSTANCE = new EntityUpdateDataSummarySnippetJson();

  private EntityUpdateDataSummarySnippetJson()
  {
    super(EntityUpdateDataSummarySnippet.class, new Object[] { "activityContentSanitizedHtml", "actorOid", "bodySanitizedHtml", "entityActorOid", "headerSanitizedHtml", "resharedActorOid" });
  }

  public static EntityUpdateDataSummarySnippetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityUpdateDataSummarySnippetJson
 * JD-Core Version:    0.6.2
 */