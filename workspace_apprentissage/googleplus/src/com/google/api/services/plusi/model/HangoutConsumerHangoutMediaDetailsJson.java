package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutConsumerHangoutMediaDetailsJson extends EsJson<HangoutConsumerHangoutMediaDetails>
{
  static final HangoutConsumerHangoutMediaDetailsJson INSTANCE = new HangoutConsumerHangoutMediaDetailsJson();

  private HangoutConsumerHangoutMediaDetailsJson()
  {
    super(HangoutConsumerHangoutMediaDetails.class, new Object[] { "hangoutMediaUrl" });
  }

  public static HangoutConsumerHangoutMediaDetailsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutConsumerHangoutMediaDetailsJson
 * JD-Core Version:    0.6.2
 */