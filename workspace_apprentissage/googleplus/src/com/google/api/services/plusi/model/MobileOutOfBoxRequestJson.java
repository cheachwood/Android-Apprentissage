package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MobileOutOfBoxRequestJson extends EsJson<MobileOutOfBoxRequest>
{
  static final MobileOutOfBoxRequestJson INSTANCE = new MobileOutOfBoxRequestJson();

  private MobileOutOfBoxRequestJson()
  {
    super(MobileOutOfBoxRequest.class, new Object[] { OutOfBoxActionJson.class, "action", "clientType", ApiaryFieldsJson.class, "commonFields", "continueUrl", "enableTracing", OutOfBoxInputFieldJson.class, "input", "integrated", "invitationToken", "partnerId", "postMessageTargetOrigin", "upgradeOrigin", "webClientPathAndQuery" });
  }

  public static MobileOutOfBoxRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MobileOutOfBoxRequestJson
 * JD-Core Version:    0.6.2
 */