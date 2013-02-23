package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class OpeningHoursProtoDay extends GenericJson
{
  public Boolean currentDay;
  public String dayName;
  public List<OpeningHoursProtoDayInterval> interval;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OpeningHoursProtoDay
 * JD-Core Version:    0.6.2
 */