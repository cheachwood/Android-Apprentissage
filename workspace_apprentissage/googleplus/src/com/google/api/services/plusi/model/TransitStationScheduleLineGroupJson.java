package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TransitStationScheduleLineGroupJson extends EsJson<TransitStationScheduleLineGroup>
{
  static final TransitStationScheduleLineGroupJson INSTANCE = new TransitStationScheduleLineGroupJson();

  private TransitStationScheduleLineGroupJson()
  {
    super(TransitStationScheduleLineGroup.class, new Object[] { "displayAsTrain", "groupHeader", TransitStationScheduleLineJson.class, "line", "moreLinesAvailable", "vehicleTypeIcon" });
  }

  public static TransitStationScheduleLineGroupJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleLineGroupJson
 * JD-Core Version:    0.6.2
 */