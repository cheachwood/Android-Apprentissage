package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetActivitiesCommonParamsJson extends EsJson<GetActivitiesCommonParams>
{
  static final GetActivitiesCommonParamsJson INSTANCE = new GetActivitiesCommonParamsJson();

  private GetActivitiesCommonParamsJson()
  {
    super(GetActivitiesCommonParams.class, arrayOfObject);
  }

  public static GetActivitiesCommonParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetActivitiesCommonParamsJson
 * JD-Core Version:    0.6.2
 */