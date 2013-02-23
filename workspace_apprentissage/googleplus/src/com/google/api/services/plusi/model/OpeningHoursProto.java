package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OpeningHoursProto extends GenericJson
{
  public PlacePageLink attribution;
  public List<OpeningHoursProtoDay> day;
  public TimeScheduleProto timeSchedule;
  public String title;
  public OpeningHoursProtoDay today;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OpeningHoursProto
 * JD-Core Version:    0.6.2
 */