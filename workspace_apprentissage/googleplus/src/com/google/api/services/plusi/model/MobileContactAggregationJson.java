package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileContactAggregationJson extends EsJson<MobileContactAggregation>
{
  static final MobileContactAggregationJson INSTANCE = new MobileContactAggregationJson();

  private MobileContactAggregationJson()
  {
    super(MobileContactAggregation.class, new Object[] { DataCircleMemberIdJson.class, "linkedIdentity" });
  }

  public static MobileContactAggregationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileContactAggregationJson
 * JD-Core Version:    0.6.2
 */