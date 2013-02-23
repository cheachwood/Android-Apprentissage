package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LiveMapsProtoJson extends EsJson<LiveMapsProto>
{
  static final LiveMapsProtoJson INSTANCE = new LiveMapsProtoJson();

  private LiveMapsProtoJson()
  {
    super(LiveMapsProto.class, new Object[] { LiveMapsPPEventJson.class, "event", PlacePageLinkJson.class, "moreEventsLink", PlacePageLinkJson.class, "provider", StoryTitleJson.class, "title" });
  }

  public static LiveMapsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LiveMapsProtoJson
 * JD-Core Version:    0.6.2
 */