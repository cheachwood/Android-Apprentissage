package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HotelPriceProtoJson extends EsJson<HotelPriceProto>
{
  static final HotelPriceProtoJson INSTANCE = new HotelPriceProtoJson();

  private HotelPriceProtoJson()
  {
    super(HotelPriceProto.class, new Object[] { "checkin", "checkinHr", "checkout", "checkoutHr", "conversionDisclaimerLink", "formattedLowestBasePrice", "formattedLowestPrice", "formattedLowestRawBasePrice", "formattedLowestRawPrice", "formattedPrefixCurrency", "formattedSuffixCurrency", HotelPriceProtoHotelLoggingDataJson.class, "hotelLoggingData", HotelPriceProtoHotelPartnerJson.class, "owner", HotelPriceProtoHotelPartnerJson.class, "partner", "taxesInclusive" });
  }

  public static HotelPriceProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HotelPriceProtoJson
 * JD-Core Version:    0.6.2
 */