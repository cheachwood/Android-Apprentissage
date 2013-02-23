package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutDataBroadcastDetailsJson extends EsJson<HangoutDataBroadcastDetails>
{
  static final HangoutDataBroadcastDetailsJson INSTANCE = new HangoutDataBroadcastDetailsJson();

  private HangoutDataBroadcastDetailsJson()
  {
    super(HangoutDataBroadcastDetails.class, new Object[] { "broadcastTitle", "broadcastUrl", "youtubeLiveId" });
  }

  public static HangoutDataBroadcastDetailsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutDataBroadcastDetailsJson
 * JD-Core Version:    0.6.2
 */