package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TransitStationScheduleLineJson extends EsJson<TransitStationScheduleLine>
{
  static final TransitStationScheduleLineJson INSTANCE = new TransitStationScheduleLineJson();

  private TransitStationScheduleLineJson()
  {
    super(TransitStationScheduleLine.class, new Object[] { "destinations", "lineId", LineSnippetJson.class, "lineSnippet", PlacePageLinkJson.class, "link", TransitStationScheduleTripJson.class, "trip" });
  }

  public static TransitStationScheduleLineJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleLineJson
 * JD-Core Version:    0.6.2
 */