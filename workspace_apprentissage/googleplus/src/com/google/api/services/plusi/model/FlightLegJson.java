package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FlightLegJson extends EsJson<FlightLeg>
{
  static final FlightLegJson INSTANCE = new FlightLegJson();

  private FlightLegJson()
  {
    super(FlightLeg.class, new Object[] { "arrivalAirportCode", "arrivalCityName", "arrivalTimestring", "carrierCode", "carrierName", "confirmationCode", "departureAirportCode", "departureCityName", "departureTimestring", "flightNumber" });
  }

  public static FlightLegJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FlightLegJson
 * JD-Core Version:    0.6.2
 */