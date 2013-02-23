package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhoneNumberJson extends EsJson<PhoneNumber>
{
  static final PhoneNumberJson INSTANCE = new PhoneNumberJson();

  private PhoneNumberJson()
  {
    super(PhoneNumber.class, arrayOfObject);
  }

  public static PhoneNumberJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhoneNumberJson
 * JD-Core Version:    0.6.2
 */