package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EventCommentJson extends EsJson<EventComment>
{
  static final EventCommentJson INSTANCE = new EventCommentJson();

  private EventCommentJson()
  {
    super(EventComment.class, new Object[] { "eventId", "eventOwnerObfuscatedId", "isBroadcastView" });
  }

  public static EventCommentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EventCommentJson
 * JD-Core Version:    0.6.2
 */