package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PushAddressJson extends EsJson<PushAddress>
{
  static final PushAddressJson INSTANCE = new PushAddressJson();

  private PushAddressJson()
  {
    super(PushAddress.class, new Object[] { PushAddressAndroidPushAddressJson.class, "androidPushAddress", PushAddressApplePushAddressJson.class, "applePushAddress" });
  }

  public static PushAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PushAddressJson
 * JD-Core Version:    0.6.2
 */