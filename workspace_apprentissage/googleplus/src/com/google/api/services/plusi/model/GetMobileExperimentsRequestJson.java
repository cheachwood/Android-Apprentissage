package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileExperimentsRequestJson extends EsJson<GetMobileExperimentsRequest>
{
  static final GetMobileExperimentsRequestJson INSTANCE = new GetMobileExperimentsRequestJson();

  private GetMobileExperimentsRequestJson()
  {
    super(GetMobileExperimentsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", GetMobileExperimentsRequestRequestedFlagJson.class, "requestedflag" });
  }

  public static GetMobileExperimentsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileExperimentsRequestJson
 * JD-Core Version:    0.6.2
 */