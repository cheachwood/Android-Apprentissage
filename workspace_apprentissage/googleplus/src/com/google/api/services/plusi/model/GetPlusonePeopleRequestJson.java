package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetPlusonePeopleRequestJson extends EsJson<GetPlusonePeopleRequest>
{
  static final GetPlusonePeopleRequestJson INSTANCE = new GetPlusonePeopleRequestJson();

  private GetPlusonePeopleRequestJson()
  {
    super(GetPlusonePeopleRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "numPeopleToReturn", "plusoneId" });
  }

  public static GetPlusonePeopleRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetPlusonePeopleRequestJson
 * JD-Core Version:    0.6.2
 */