package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventThirdPartyInfoJson extends EsJson<EventThirdPartyInfo>
{
  static final EventThirdPartyInfoJson INSTANCE = new EventThirdPartyInfoJson();

  private EventThirdPartyInfoJson()
  {
    super(EventThirdPartyInfo.class, new Object[] { "parkingInfo", "thirdPartyEventUrl", "ticketSellerUrl", "videoUrl" });
  }

  public static EventThirdPartyInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventThirdPartyInfoJson
 * JD-Core Version:    0.6.2
 */