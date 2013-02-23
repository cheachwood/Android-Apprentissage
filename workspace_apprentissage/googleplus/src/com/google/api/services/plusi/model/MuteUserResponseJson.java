package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MuteUserResponseJson extends EsJson<MuteUserResponse>
{
  static final MuteUserResponseJson INSTANCE = new MuteUserResponseJson();

  private MuteUserResponseJson()
  {
    super(MuteUserResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "isMuted", "obfuscatedGaiaId", "versionInfo" });
  }

  public static MuteUserResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MuteUserResponseJson
 * JD-Core Version:    0.6.2
 */