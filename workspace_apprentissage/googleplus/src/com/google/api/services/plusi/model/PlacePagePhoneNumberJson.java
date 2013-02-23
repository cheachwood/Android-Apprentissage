package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlacePagePhoneNumberJson extends EsJson<PlacePagePhoneNumber>
{
  static final PlacePagePhoneNumberJson INSTANCE = new PlacePagePhoneNumberJson();

  private PlacePagePhoneNumberJson()
  {
    super(PlacePagePhoneNumber.class, new Object[] { "formattedPhone", "phoneLabel", PhoneNumberJson.class, "phoneNumber", "rawPhone" });
  }

  public static PlacePagePhoneNumberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePagePhoneNumberJson
 * JD-Core Version:    0.6.2
 */