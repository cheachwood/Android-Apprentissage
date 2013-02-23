package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FindMorePeopleRequestJson extends EsJson<FindMorePeopleRequest>
{
  static final FindMorePeopleRequestJson INSTANCE = new FindMorePeopleRequestJson();

  private FindMorePeopleRequestJson()
  {
    super(FindMorePeopleRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxSuggestions", "onlyEsUsers", "onlyHighQualitySuggestions" });
  }

  public static FindMorePeopleRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FindMorePeopleRequestJson
 * JD-Core Version:    0.6.2
 */