package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class YouTubeChannelInfoJson extends EsJson<YouTubeChannelInfo>
{
  static final YouTubeChannelInfoJson INSTANCE = new YouTubeChannelInfoJson();

  private YouTubeChannelInfoJson()
  {
    super(YouTubeChannelInfo.class, new Object[] { LinksJson.class, "channelLinks", "isRestricted" });
  }

  public static YouTubeChannelInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.YouTubeChannelInfoJson
 * JD-Core Version:    0.6.2
 */