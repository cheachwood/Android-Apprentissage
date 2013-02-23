package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BusinessHoursFieldJson extends EsJson<BusinessHoursField>
{
  static final BusinessHoursFieldJson INSTANCE = new BusinessHoursFieldJson();

  private BusinessHoursFieldJson()
  {
    super(BusinessHoursField.class, new Object[] { WeekdayTimeIntervalsJson.class, "days", MetadataJson.class, "metadata" });
  }

  public static BusinessHoursFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BusinessHoursFieldJson
 * JD-Core Version:    0.6.2
 */