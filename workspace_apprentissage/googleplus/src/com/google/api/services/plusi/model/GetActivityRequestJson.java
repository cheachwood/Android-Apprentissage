package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetActivityRequestJson extends EsJson<GetActivityRequest>
{
  static final GetActivityRequestJson INSTANCE = new GetActivityRequestJson();

  private GetActivityRequestJson()
  {
    super(GetActivityRequest.class, arrayOfObject);
  }

  public static GetActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivityRequestJson
 * JD-Core Version:    0.6.2
 */