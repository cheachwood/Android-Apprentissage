package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class SettingsSetResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public Boolean success;
  public SetResponseWhoCanCommentResponse whoCanCommentResponse;
  public SetResponseWhoCanNotifyResponse whoCanNotifyResponse;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsSetResponse
 * JD-Core Version:    0.6.2
 */