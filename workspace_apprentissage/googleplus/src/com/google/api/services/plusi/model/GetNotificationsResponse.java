package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GetNotificationsResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public DataNotificationsData notificationsData;
  public String obfuscatedGaiaId;
  public Boolean refreshRequired;
  public ViewerData viewerData;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetNotificationsResponse
 * JD-Core Version:    0.6.2
 */