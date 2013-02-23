package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditSegmentsJson extends EsJson<EditSegments>
{
  static final EditSegmentsJson INSTANCE = new EditSegmentsJson();

  private EditSegmentsJson()
  {
    super(EditSegments.class, new Object[] { SegmentJson.class, "segments" });
  }

  public static EditSegmentsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditSegmentsJson
 * JD-Core Version:    0.6.2
 */