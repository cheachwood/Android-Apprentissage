package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TravelEventJson extends EsJson<TravelEvent>
{
  static final TravelEventJson INSTANCE = new TravelEventJson();

  private TravelEventJson()
  {
    super(TravelEvent.class, new Object[] { "description", FlightLegJson.class, "flight", "imageUrl", "name", "url" });
  }

  public static TravelEventJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TravelEventJson
 * JD-Core Version:    0.6.2
 */