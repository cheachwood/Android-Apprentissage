package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharingRosterJson extends EsJson<SharingRoster>
{
  static final SharingRosterJson INSTANCE = new SharingRosterJson();

  private SharingRosterJson()
  {
    super(SharingRoster.class, new Object[] { SharingTargetIdJson.class, "requiredScopeId", SharingTargetIdJson.class, "sharingTargetId" });
  }

  public static SharingRosterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharingRosterJson
 * JD-Core Version:    0.6.2
 */