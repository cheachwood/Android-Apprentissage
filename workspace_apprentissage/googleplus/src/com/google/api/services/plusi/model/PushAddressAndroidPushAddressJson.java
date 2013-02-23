package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PushAddressAndroidPushAddressJson extends EsJson<PushAddressAndroidPushAddress>
{
  static final PushAddressAndroidPushAddressJson INSTANCE = new PushAddressAndroidPushAddressJson();

  private PushAddressAndroidPushAddressJson()
  {
    super(PushAddressAndroidPushAddress.class, new Object[] { "registrationId" });
  }

  public static PushAddressAndroidPushAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PushAddressAndroidPushAddressJson
 * JD-Core Version:    0.6.2
 */