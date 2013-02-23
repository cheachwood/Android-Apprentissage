package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileContactJson extends EsJson<MobileContact>
{
  static final MobileContactJson INSTANCE = new MobileContactJson();

  private MobileContactJson()
  {
    super(MobileContact.class, new Object[] { MobileContactAffinityJson.class, "affinity", MobileContactAggregationJson.class, "aggregation", DataCircleMemberIdJson.class, "id" });
  }

  public static MobileContactJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileContactJson
 * JD-Core Version:    0.6.2
 */