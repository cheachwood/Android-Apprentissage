package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RelationshipInterestJson extends EsJson<RelationshipInterest>
{
  static final RelationshipInterestJson INSTANCE = new RelationshipInterestJson();

  private RelationshipInterestJson()
  {
    super(RelationshipInterest.class, new Object[] { "value" });
  }

  public static RelationshipInterestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RelationshipInterestJson
 * JD-Core Version:    0.6.2
 */