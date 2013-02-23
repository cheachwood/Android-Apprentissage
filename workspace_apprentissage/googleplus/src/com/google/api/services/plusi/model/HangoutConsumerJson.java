package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutConsumerJson extends EsJson<HangoutConsumer>
{
  static final HangoutConsumerJson INSTANCE = new HangoutConsumerJson();

  private HangoutConsumerJson()
  {
    super(HangoutConsumer.class, new Object[] { EmbedsPersonJson.class, "attendees", "eventId", HangoutConsumerHangoutMediaDetailsJson.class, "hangoutMediaDetails", "hashedRoomId", "isBroadcastInvite", "isSelfPost", "maxAttendees", "minorsNotAllowed", "name", "preferredLanguage", "region", HangoutStartContextJson.class, "startContext", "status", "topicMaybeNsfw", "totalAttendeeCount", "url", "youtubeLiveId" });
  }

  public static HangoutConsumerJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutConsumerJson
 * JD-Core Version:    0.6.2
 */