package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileExperimentsResponseExperimentValueJson extends EsJson<GetMobileExperimentsResponseExperimentValue>
{
  static final GetMobileExperimentsResponseExperimentValueJson INSTANCE = new GetMobileExperimentsResponseExperimentValueJson();

  private GetMobileExperimentsResponseExperimentValueJson()
  {
    super(GetMobileExperimentsResponseExperimentValue.class, arrayOfObject);
  }

  public static GetMobileExperimentsResponseExperimentValueJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileExperimentsResponseExperimentValueJson
 * JD-Core Version:    0.6.2
 */