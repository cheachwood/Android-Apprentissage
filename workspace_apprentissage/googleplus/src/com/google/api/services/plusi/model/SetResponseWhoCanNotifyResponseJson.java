package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetResponseWhoCanNotifyResponseJson extends EsJson<SetResponseWhoCanNotifyResponse>
{
  static final SetResponseWhoCanNotifyResponseJson INSTANCE = new SetResponseWhoCanNotifyResponseJson();

  private SetResponseWhoCanNotifyResponseJson()
  {
    super(SetResponseWhoCanNotifyResponse.class, new Object[] { "aclJson" });
  }

  public static SetResponseWhoCanNotifyResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetResponseWhoCanNotifyResponseJson
 * JD-Core Version:    0.6.2
 */