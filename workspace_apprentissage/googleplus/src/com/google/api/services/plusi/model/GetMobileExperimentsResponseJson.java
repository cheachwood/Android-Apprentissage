package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileExperimentsResponseJson extends EsJson<GetMobileExperimentsResponse>
{
  static final GetMobileExperimentsResponseJson INSTANCE = new GetMobileExperimentsResponseJson();

  private GetMobileExperimentsResponseJson()
  {
    super(GetMobileExperimentsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", GetMobileExperimentsResponseExperimentJson.class, "experiment" });
  }

  public static GetMobileExperimentsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileExperimentsResponseJson
 * JD-Core Version:    0.6.2
 */