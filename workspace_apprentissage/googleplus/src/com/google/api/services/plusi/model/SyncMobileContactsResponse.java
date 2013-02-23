package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SyncMobileContactsResponse extends GenericJson
{
  public Boolean aggregationSyncRequired;
  public TraceRecords backendTrace;
  public List<DataCircleMemberId> failedContact;
  public String status;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SyncMobileContactsResponse
 * JD-Core Version:    0.6.2
 */