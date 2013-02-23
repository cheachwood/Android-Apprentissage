package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityRecommendedPeopleDataJson extends EsJson<EntityRecommendedPeopleData>
{
  static final EntityRecommendedPeopleDataJson INSTANCE = new EntityRecommendedPeopleDataJson();

  private EntityRecommendedPeopleDataJson()
  {
    super(EntityRecommendedPeopleData.class, new Object[] { EmbedsPersonJson.class, "recommendedPerson", "suggestionMessage" });
  }

  public static EntityRecommendedPeopleDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityRecommendedPeopleDataJson
 * JD-Core Version:    0.6.2
 */