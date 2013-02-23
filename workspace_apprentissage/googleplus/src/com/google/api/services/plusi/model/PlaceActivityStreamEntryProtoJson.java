package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlaceActivityStreamEntryProtoJson extends EsJson<PlaceActivityStreamEntryProto>
{
  static final PlaceActivityStreamEntryProtoJson INSTANCE = new PlaceActivityStreamEntryProtoJson();

  private PlaceActivityStreamEntryProtoJson()
  {
    super(PlaceActivityStreamEntryProto.class, new Object[] { PlacePageCheckinJson.class, "checkin", UploadedMediaJson.class, "media", GoogleReviewProtoJson.class, "review" });
  }

  public static PlaceActivityStreamEntryProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlaceActivityStreamEntryProtoJson
 * JD-Core Version:    0.6.2
 */