package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SyncMobileContactsRequestJson extends EsJson<SyncMobileContactsRequest>
{
  static final SyncMobileContactsRequestJson INSTANCE = new SyncMobileContactsRequestJson();

  private SyncMobileContactsRequestJson()
  {
    super(SyncMobileContactsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", MobileContactJson.class, "contact", "device", "devicePhoneNumber", "enableTracing", "type" });
  }

  public static SyncMobileContactsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SyncMobileContactsRequestJson
 * JD-Core Version:    0.6.2
 */