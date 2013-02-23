package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MuteActivityResponseJson extends EsJson<MuteActivityResponse>
{
  static final MuteActivityResponseJson INSTANCE = new MuteActivityResponseJson();

  private MuteActivityResponseJson()
  {
    super(MuteActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "itemId" });
  }

  public static MuteActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MuteActivityResponseJson
 * JD-Core Version:    0.6.2
 */