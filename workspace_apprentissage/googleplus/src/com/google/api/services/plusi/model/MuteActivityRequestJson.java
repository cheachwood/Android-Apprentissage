package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MuteActivityRequestJson extends EsJson<MuteActivityRequest>
{
  static final MuteActivityRequestJson INSTANCE = new MuteActivityRequestJson();

  private MuteActivityRequestJson()
  {
    super(MuteActivityRequest.class, new Object[] { "activityId", ApiaryFieldsJson.class, "commonFields", "enableTracing", "muteState" });
  }

  public static MuteActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MuteActivityRequestJson
 * JD-Core Version:    0.6.2
 */