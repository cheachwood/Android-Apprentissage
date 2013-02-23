package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OzDataSettingsJson extends EsJson<OzDataSettings>
{
  static final OzDataSettingsJson INSTANCE = new OzDataSettingsJson();

  private OzDataSettingsJson()
  {
    super(OzDataSettings.class, new Object[] { DataBirthdaySettingsJson.class, "birthdaySettings", DataGadgetsSettingsJson.class, "gadgetsSettings", DataHuddleSettingsJson.class, "huddleSettings", DataMobileSettingsJson.class, "mobileSettings", DataNotificationSettingsJson.class, "notificationSettings", DataPlusActionsSettingsJson.class, "plusActionsSettings", DataSocialAdsJson.class, "socialAds", DataWhoCanCommentSettingsJson.class, "whoCanCommentSettings", DataWhoCanInterruptSettingsJson.class, "whoCanInterruptSettings", DataWhoCanNotifySettingsJson.class, "whoCanNotifySettings" });
  }

  public static OzDataSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OzDataSettingsJson
 * JD-Core Version:    0.6.2
 */