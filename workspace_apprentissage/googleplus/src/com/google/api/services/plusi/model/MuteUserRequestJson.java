package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MuteUserRequestJson extends EsJson<MuteUserRequest>
{
  static final MuteUserRequestJson INSTANCE = new MuteUserRequestJson();

  private MuteUserRequestJson()
  {
    super(MuteUserRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "obfuscatedGaiaId", "shouldMute" });
  }

  public static MuteUserRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MuteUserRequestJson
 * JD-Core Version:    0.6.2
 */