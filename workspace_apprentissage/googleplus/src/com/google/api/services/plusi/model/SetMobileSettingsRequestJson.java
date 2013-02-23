package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetMobileSettingsRequestJson extends EsJson<SetMobileSettingsRequest>
{
  static final SetMobileSettingsRequestJson INSTANCE = new SetMobileSettingsRequestJson();

  private SetMobileSettingsRequestJson()
  {
    super(SetMobileSettingsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", MobilePreferenceJson.class, "preference" });
  }

  public static SetMobileSettingsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetMobileSettingsRequestJson
 * JD-Core Version:    0.6.2
 */