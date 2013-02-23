package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharingTargetJson extends EsJson<SharingTarget>
{
  static final SharingTargetJson INSTANCE = new SharingTargetJson();

  private SharingTargetJson()
  {
    super(SharingTarget.class, new Object[] { "displayName", "email", SharingTargetIdJson.class, "id" });
  }

  public static SharingTargetJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharingTargetJson
 * JD-Core Version:    0.6.2
 */