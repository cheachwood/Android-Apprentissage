package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PushAddressApplePushAddressJson extends EsJson<PushAddressApplePushAddress>
{
  static final PushAddressApplePushAddressJson INSTANCE = new PushAddressApplePushAddressJson();

  private PushAddressApplePushAddressJson()
  {
    super(PushAddressApplePushAddress.class, new Object[] { "applicationId", "deviceToken" });
  }

  public static PushAddressApplePushAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PushAddressApplePushAddressJson
 * JD-Core Version:    0.6.2
 */