package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SegmentationInfoJson extends EsJson<SegmentationInfo>
{
  static final SegmentationInfoJson INSTANCE = new SegmentationInfoJson();

  private SegmentationInfoJson()
  {
    super(SegmentationInfo.class, new Object[] { "isUserLapsed", "userSegment" });
  }

  public static SegmentationInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SegmentationInfoJson
 * JD-Core Version:    0.6.2
 */