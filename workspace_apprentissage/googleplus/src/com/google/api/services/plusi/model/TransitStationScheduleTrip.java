package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class TransitStationScheduleTrip extends GenericJson
{
  public List<TransitStationScheduleDeparture> departure;
  public TransitStationScheduleDeparture firstDeparture;
  public String headsign;
  public TransitStationScheduleDeparture lastDeparture;
  public PlacePageLink link;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleTrip
 * JD-Core Version:    0.6.2
 */