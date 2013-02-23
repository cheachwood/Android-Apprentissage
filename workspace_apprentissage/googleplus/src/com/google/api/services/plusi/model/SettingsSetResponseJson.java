package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SettingsSetResponseJson extends EsJson<SettingsSetResponse>
{
  static final SettingsSetResponseJson INSTANCE = new SettingsSetResponseJson();

  private SettingsSetResponseJson()
  {
    super(SettingsSetResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "success", SetResponseWhoCanCommentResponseJson.class, "whoCanCommentResponse", SetResponseWhoCanNotifyResponseJson.class, "whoCanNotifyResponse" });
  }

  public static SettingsSetResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SettingsSetResponseJson
 * JD-Core Version:    0.6.2
 */