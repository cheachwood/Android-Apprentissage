package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileContactAffinityJson extends EsJson<MobileContactAffinity>
{
  static final MobileContactAffinityJson INSTANCE = new MobileContactAffinityJson();

  private MobileContactAffinityJson()
  {
    super(MobileContactAffinity.class, arrayOfObject);
  }

  public static MobileContactAffinityJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileContactAffinityJson
 * JD-Core Version:    0.6.2
 */