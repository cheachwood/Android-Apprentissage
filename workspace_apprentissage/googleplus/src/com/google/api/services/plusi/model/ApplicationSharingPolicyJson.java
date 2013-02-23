package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ApplicationSharingPolicyJson extends EsJson<ApplicationSharingPolicy>
{
  static final ApplicationSharingPolicyJson INSTANCE = new ApplicationSharingPolicyJson();

  private ApplicationSharingPolicyJson()
  {
    super(ApplicationSharingPolicy.class, new Object[] { "allowSquares", "allowedGroupType", "applicationId", "showUnderageWarnings" });
  }

  public static ApplicationSharingPolicyJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ApplicationSharingPolicyJson
 * JD-Core Version:    0.6.2
 */