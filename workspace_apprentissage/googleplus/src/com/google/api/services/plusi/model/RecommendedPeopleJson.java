package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RecommendedPeopleJson extends EsJson<RecommendedPeople>
{
  static final RecommendedPeopleJson INSTANCE = new RecommendedPeopleJson();

  private RecommendedPeopleJson()
  {
    super(RecommendedPeople.class, new Object[] { "id", EmbedsPersonJson.class, "member", "name", EmbedsPersonJson.class, "sharer", "url" });
  }

  public static RecommendedPeopleJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RecommendedPeopleJson
 * JD-Core Version:    0.6.2
 */