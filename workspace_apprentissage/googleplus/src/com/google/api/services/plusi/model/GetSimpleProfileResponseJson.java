package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetSimpleProfileResponseJson extends EsJson<GetSimpleProfileResponse>
{
  static final GetSimpleProfileResponseJson INSTANCE = new GetSimpleProfileResponseJson();

  private GetSimpleProfileResponseJson()
  {
    super(GetSimpleProfileResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", SimpleProfileJson.class, "profile" });
  }

  public static GetSimpleProfileResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSimpleProfileResponseJson
 * JD-Core Version:    0.6.2
 */