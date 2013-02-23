package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileSettingsResponseJson extends EsJson<GetMobileSettingsResponse>
{
  static final GetMobileSettingsResponseJson INSTANCE = new GetMobileSettingsResponseJson();

  private GetMobileSettingsResponseJson()
  {
    super(GetMobileSettingsResponse.class, new Object[] { MobileSettingsApplicationJson.class, "application", TraceRecordsJson.class, "backendTrace", MobilePreferenceJson.class, "preference", "requestError", ShareboxSettingsJson.class, "shareboxSettings", "speedRacerDefaultSetting", MobileSettingsUserJson.class, "user" });
  }

  public static GetMobileSettingsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileSettingsResponseJson
 * JD-Core Version:    0.6.2
 */