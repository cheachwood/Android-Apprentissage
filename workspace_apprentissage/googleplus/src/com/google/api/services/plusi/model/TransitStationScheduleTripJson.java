package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TransitStationScheduleTripJson extends EsJson<TransitStationScheduleTrip>
{
  static final TransitStationScheduleTripJson INSTANCE = new TransitStationScheduleTripJson();

  private TransitStationScheduleTripJson()
  {
    super(TransitStationScheduleTrip.class, new Object[] { TransitStationScheduleDepartureJson.class, "departure", TransitStationScheduleDepartureJson.class, "firstDeparture", "headsign", TransitStationScheduleDepartureJson.class, "lastDeparture", PlacePageLinkJson.class, "link" });
  }

  public static TransitStationScheduleTripJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleTripJson
 * JD-Core Version:    0.6.2
 */