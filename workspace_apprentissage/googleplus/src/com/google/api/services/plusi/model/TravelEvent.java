package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class TravelEvent extends GenericJson
{
  public String description;
  public List<FlightLeg> flight;
  public String imageUrl;
  public String name;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TravelEvent
 * JD-Core Version:    0.6.2
 */