package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class HangoutBroadcast extends GenericJson
{
  public List<EmbedsPerson> attendees;
  public HangoutConsumer baseHangout;
  public String hashedRoomId;
  public String name;
  public String preferredLanguage;
  public String region;
  public HangoutStartContext startContext;
  public String status;
  public Boolean topicMaybeNsfw;
  public Integer totalAttendeeCount;
  public String url;
  public String youtubeLiveId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutBroadcast
 * JD-Core Version:    0.6.2
 */