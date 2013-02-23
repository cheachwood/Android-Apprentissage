package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetMobileSettingsRequestJson extends EsJson<GetMobileSettingsRequest>
{
  static final GetMobileSettingsRequestJson INSTANCE = new GetMobileSettingsRequestJson();

  private GetMobileSettingsRequestJson()
  {
    super(GetMobileSettingsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static GetMobileSettingsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetMobileSettingsRequestJson
 * JD-Core Version:    0.6.2
 */