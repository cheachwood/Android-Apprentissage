package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class HangoutConsumer extends GenericJson
{
  public List<EmbedsPerson> attendees;
  public String eventId;
  public List<HangoutConsumerHangoutMediaDetails> hangoutMediaDetails;
  public String hashedRoomId;
  public Boolean isBroadcastInvite;
  public Boolean isSelfPost;
  public Integer maxAttendees;
  public Boolean minorsNotAllowed;
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
 * Qualified Name:     com.google.api.services.plusi.model.HangoutConsumer
 * JD-Core Version:    0.6.2
 */