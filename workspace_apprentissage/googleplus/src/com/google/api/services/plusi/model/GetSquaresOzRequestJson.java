package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetSquaresOzRequestJson extends EsJson<GetSquaresOzRequest>
{
  static final GetSquaresOzRequestJson INSTANCE = new GetSquaresOzRequestJson();

  private GetSquaresOzRequestJson()
  {
    super(GetSquaresOzRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "consistentRead", "enableTracing", "includePeopleInCommon", "squareType" });
  }

  public static GetSquaresOzRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSquaresOzRequestJson
 * JD-Core Version:    0.6.2
 */