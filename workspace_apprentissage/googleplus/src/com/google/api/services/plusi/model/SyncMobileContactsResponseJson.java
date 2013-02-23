package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SyncMobileContactsResponseJson extends EsJson<SyncMobileContactsResponse>
{
  static final SyncMobileContactsResponseJson INSTANCE = new SyncMobileContactsResponseJson();

  private SyncMobileContactsResponseJson()
  {
    super(SyncMobileContactsResponse.class, new Object[] { "aggregationSyncRequired", TraceRecordsJson.class, "backendTrace", DataCircleMemberIdJson.class, "failedContact", "status" });
  }

  public static SyncMobileContactsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SyncMobileContactsResponseJson
 * JD-Core Version:    0.6.2
 */