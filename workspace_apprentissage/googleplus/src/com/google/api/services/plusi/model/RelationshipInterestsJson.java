package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RelationshipInterestsJson extends EsJson<RelationshipInterests>
{
  static final RelationshipInterestsJson INSTANCE = new RelationshipInterestsJson();

  private RelationshipInterestsJson()
  {
    super(RelationshipInterests.class, new Object[] { RelationshipInterestJson.class, "interest", MetadataJson.class, "metadata" });
  }

  public static RelationshipInterestsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RelationshipInterestsJson
 * JD-Core Version:    0.6.2
 */