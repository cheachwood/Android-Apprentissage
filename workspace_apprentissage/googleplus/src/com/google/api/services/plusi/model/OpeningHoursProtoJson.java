package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OpeningHoursProtoJson extends EsJson<OpeningHoursProto>
{
  static final OpeningHoursProtoJson INSTANCE = new OpeningHoursProtoJson();

  private OpeningHoursProtoJson()
  {
    super(OpeningHoursProto.class, new Object[] { PlacePageLinkJson.class, "attribution", OpeningHoursProtoDayJson.class, "day", TimeScheduleProtoJson.class, "timeSchedule", "title", OpeningHoursProtoDayJson.class, "today" });
  }

  public static OpeningHoursProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OpeningHoursProtoJson
 * JD-Core Version:    0.6.2
 */