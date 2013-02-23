package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetMobileSettingsResponseJson extends EsJson<SetMobileSettingsResponse>
{
  static final SetMobileSettingsResponseJson INSTANCE = new SetMobileSettingsResponseJson();

  private SetMobileSettingsResponseJson()
  {
    super(SetMobileSettingsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static SetMobileSettingsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetMobileSettingsResponseJson
 * JD-Core Version:    0.6.2
 */