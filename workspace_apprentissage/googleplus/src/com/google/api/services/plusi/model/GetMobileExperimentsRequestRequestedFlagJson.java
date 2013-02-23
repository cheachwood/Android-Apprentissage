package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileExperimentsRequestRequestedFlagJson extends EsJson<GetMobileExperimentsRequestRequestedFlag>
{
  static final GetMobileExperimentsRequestRequestedFlagJson INSTANCE = new GetMobileExperimentsRequestRequestedFlagJson();

  private GetMobileExperimentsRequestRequestedFlagJson()
  {
    super(GetMobileExperimentsRequestRequestedFlag.class, new Object[] { "flagId" });
  }

  public static GetMobileExperimentsRequestRequestedFlagJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileExperimentsRequestRequestedFlagJson
 * JD-Core Version:    0.6.2
 */