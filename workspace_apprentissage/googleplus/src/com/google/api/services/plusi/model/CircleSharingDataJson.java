package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CircleSharingDataJson extends EsJson<CircleSharingData>
{
  static final CircleSharingDataJson INSTANCE = new CircleSharingDataJson();

  private CircleSharingDataJson()
  {
    super(CircleSharingData.class, new Object[] { "circleName", "description", "memberKey", CommonPersonJson.class, "personForDisplay", "sharerGender", "sharerName" });
  }

  public static CircleSharingDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CircleSharingDataJson
 * JD-Core Version:    0.6.2
 */