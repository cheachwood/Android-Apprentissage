package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharingTargetIdJson extends EsJson<SharingTargetId>
{
  static final SharingTargetIdJson INSTANCE = new SharingTargetIdJson();

  private SharingTargetIdJson()
  {
    super(SharingTargetId.class, new Object[] { "circleId", EventTargetIdJson.class, "eventId", "groupType", DataCircleMemberIdJson.class, "personId", SquareTargetIdJson.class, "squareId" });
  }

  public static SharingTargetIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharingTargetIdJson
 * JD-Core Version:    0.6.2
 */