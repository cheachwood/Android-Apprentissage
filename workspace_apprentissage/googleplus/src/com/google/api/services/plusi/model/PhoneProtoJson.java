package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhoneProtoJson extends EsJson<PhoneProto>
{
  static final PhoneProtoJson INSTANCE = new PhoneProtoJson();

  private PhoneProtoJson()
  {
    super(PhoneProto.class, new Object[] { "invalidated", PlacePagePhoneNumberJson.class, "phoneNumber" });
  }

  public static PhoneProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhoneProtoJson
 * JD-Core Version:    0.6.2
 */