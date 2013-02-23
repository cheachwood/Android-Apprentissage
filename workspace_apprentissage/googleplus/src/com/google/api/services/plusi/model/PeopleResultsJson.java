package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PeopleResultsJson extends EsJson<PeopleResults>
{
  static final PeopleResultsJson INSTANCE = new PeopleResultsJson();

  private PeopleResultsJson()
  {
    super(PeopleResults.class, new Object[] { PeopleResultJson.class, "result", "shownPeopleBlob" });
  }

  public static PeopleResultsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PeopleResultsJson
 * JD-Core Version:    0.6.2
 */