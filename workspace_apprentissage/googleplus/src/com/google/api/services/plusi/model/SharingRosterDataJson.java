package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharingRosterDataJson extends EsJson<SharingRosterData>
{
  static final SharingRosterDataJson INSTANCE = new SharingRosterDataJson();

  private SharingRosterDataJson()
  {
    super(SharingRosterData.class, new Object[] { ApplicationSharingPolicyJson.class, "applicationPolicy", SharingTargetJson.class, "targets" });
  }

  public static SharingRosterDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharingRosterDataJson
 * JD-Core Version:    0.6.2
 */