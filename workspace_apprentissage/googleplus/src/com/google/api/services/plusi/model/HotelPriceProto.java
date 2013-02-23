package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class HotelPriceProto extends GenericJson
{
  public String checkin;
  public String checkinHr;
  public String checkout;
  public String checkoutHr;
  public String conversionDisclaimerLink;
  public String formattedLowestBasePrice;
  public String formattedLowestPrice;
  public String formattedLowestRawBasePrice;
  public String formattedLowestRawPrice;
  public String formattedPrefixCurrency;
  public String formattedSuffixCurrency;
  public HotelPriceProtoHotelLoggingData hotelLoggingData;
  public HotelPriceProtoHotelPartner owner;
  public List<HotelPriceProtoHotelPartner> partner;
  public Boolean taxesInclusive;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HotelPriceProto
 * JD-Core Version:    0.6.2
 */