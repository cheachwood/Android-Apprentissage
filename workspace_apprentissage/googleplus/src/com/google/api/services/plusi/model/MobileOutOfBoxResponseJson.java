package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileOutOfBoxResponseJson extends EsJson<MobileOutOfBoxResponse>
{
  static final MobileOutOfBoxResponseJson INSTANCE = new MobileOutOfBoxResponseJson();

  private MobileOutOfBoxResponseJson()
  {
    super(MobileOutOfBoxResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "continueUrl", OutOfBoxViewJson.class, "failureView", "postMessageTargetOrigin", "redirectUrl", "signupComplete", "smsSent", OutOfBoxViewJson.class, "view" });
  }

  public static MobileOutOfBoxResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileOutOfBoxResponseJson
 * JD-Core Version:    0.6.2
 */