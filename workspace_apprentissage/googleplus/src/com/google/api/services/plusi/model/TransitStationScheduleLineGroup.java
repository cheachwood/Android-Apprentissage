package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class TransitStationScheduleLineGroup extends GenericJson
{
  public Boolean displayAsTrain;
  public String groupHeader;
  public List<TransitStationScheduleLine> line;
  public Boolean moreLinesAvailable;
  public List<String> vehicleTypeIcon;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransitStationScheduleLineGroup
 * JD-Core Version:    0.6.2
 */