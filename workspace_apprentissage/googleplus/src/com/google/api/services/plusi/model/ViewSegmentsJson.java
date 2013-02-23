package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewSegmentsJson extends EsJson<ViewSegments>
{
  static final ViewSegmentsJson INSTANCE = new ViewSegmentsJson();

  private ViewSegmentsJson()
  {
    super(ViewSegments.class, new Object[] { SegmentJson.class, "segments" });
  }

  public static ViewSegmentsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewSegmentsJson
 * JD-Core Version:    0.6.2
 */