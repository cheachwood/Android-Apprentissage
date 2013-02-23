package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutBroadcastJson extends EsJson<HangoutBroadcast>
{
  static final HangoutBroadcastJson INSTANCE = new HangoutBroadcastJson();

  private HangoutBroadcastJson()
  {
    super(HangoutBroadcast.class, new Object[] { EmbedsPersonJson.class, "attendees", HangoutConsumerJson.class, "baseHangout", "hashedRoomId", "name", "preferredLanguage", "region", HangoutStartContextJson.class, "startContext", "status", "topicMaybeNsfw", "totalAttendeeCount", "url", "youtubeLiveId" });
  }

  public static HangoutBroadcastJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutBroadcastJson
 * JD-Core Version:    0.6.2
 */