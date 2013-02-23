package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HotelPriceProtoHotelLoggingDataJson extends EsJson<HotelPriceProtoHotelLoggingData>
{
  static final HotelPriceProtoHotelLoggingDataJson INSTANCE = new HotelPriceProtoHotelLoggingDataJson();

  private HotelPriceProtoHotelLoggingDataJson()
  {
    super(HotelPriceProtoHotelLoggingData.class, arrayOfObject);
  }

  public static HotelPriceProtoHotelLoggingDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HotelPriceProtoHotelLoggingDataJson
 * JD-Core Version:    0.6.2
 */