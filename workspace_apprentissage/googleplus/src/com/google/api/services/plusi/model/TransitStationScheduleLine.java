package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class TransitStationScheduleLine extends GenericJson
{
  public String destinations;
  public String lineId;
  public LineSnippet lineSnippet;
  public PlacePageLink link;
  public List<TransitStationScheduleTrip> trip;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleLine
 * JD-Core Version:    0.6.2
 */