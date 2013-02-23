package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RelationshipStatusJson extends EsJson<RelationshipStatus>
{
  static final RelationshipStatusJson INSTANCE = new RelationshipStatusJson();

  private RelationshipStatusJson()
  {
    super(RelationshipStatus.class, new Object[] { MetadataJson.class, "metadata", "statusMissing", "value" });
  }

  public static RelationshipStatusJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RelationshipStatusJson
 * JD-Core Version:    0.6.2
 */