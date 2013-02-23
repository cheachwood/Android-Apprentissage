package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PeopleRequestDataJson extends EsJson<PeopleRequestData>
{
  static final PeopleRequestDataJson INSTANCE = new PeopleRequestDataJson();

  private PeopleRequestDataJson()
  {
    super(PeopleRequestData.class, new Object[] { "shownPeopleBlob" });
  }

  public static PeopleRequestDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PeopleRequestDataJson
 * JD-Core Version:    0.6.2
 */