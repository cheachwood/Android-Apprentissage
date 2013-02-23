package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileGenderJson extends EsJson<MobileGender>
{
  static final MobileGenderJson INSTANCE = new MobileGenderJson();

  private MobileGenderJson()
  {
    super(MobileGender.class, new Object[] { "type" });
  }

  public static MobileGenderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileGenderJson
 * JD-Core Version:    0.6.2
 */