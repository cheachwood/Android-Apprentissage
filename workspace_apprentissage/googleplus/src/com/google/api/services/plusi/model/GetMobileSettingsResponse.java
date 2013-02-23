package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetMobileSettingsResponse extends GenericJson
{
  public MobileSettingsApplication application;
  public TraceRecords backendTrace;
  public MobilePreference preference;
  public String requestError;
  public ShareboxSettings shareboxSettings;
  public String speedRacerDefaultSetting;
  public MobileSettingsUser user;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileSettingsResponse
 * JD-Core Version:    0.6.2
 */