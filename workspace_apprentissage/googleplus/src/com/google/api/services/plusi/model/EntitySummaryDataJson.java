package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntitySummaryDataJson extends EsJson<EntitySummaryData>
{
  static final EntitySummaryDataJson INSTANCE = new EntitySummaryDataJson();

  private EntitySummaryDataJson()
  {
    super(EntitySummaryData.class, new Object[] { "summaryPlaintext", "summarySanitizedHtml" });
  }

  public static EntitySummaryDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntitySummaryDataJson
 * JD-Core Version:    0.6.2
 */