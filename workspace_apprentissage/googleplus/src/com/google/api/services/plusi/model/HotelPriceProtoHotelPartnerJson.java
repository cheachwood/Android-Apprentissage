package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HotelPriceProtoHotelPartnerJson extends EsJson<HotelPriceProtoHotelPartner>
{
  static final HotelPriceProtoHotelPartnerJson INSTANCE = new HotelPriceProtoHotelPartnerJson();

  private HotelPriceProtoHotelPartnerJson()
  {
    super(HotelPriceProtoHotelPartner.class, new Object[] { "currencyString", "formattedBasePrice", "formattedPrice", "formattedTaxes", "isOwner", "name", "price", "taxes", "url" });
  }

  public static HotelPriceProtoHotelPartnerJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HotelPriceProtoHotelPartnerJson
 * JD-Core Version:    0.6.2
 */