package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobilePreferenceJson extends EsJson<MobilePreference>
{
  static final MobilePreferenceJson INSTANCE = new MobilePreferenceJson();

  private MobilePreferenceJson()
  {
    super(MobilePreference.class, arrayOfObject);
  }

  public static MobilePreferenceJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobilePreferenceJson
 * JD-Core Version:    0.6.2
 */