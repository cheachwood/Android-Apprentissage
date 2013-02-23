package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SourceFeedJson extends EsJson<SourceFeed>
{
  static final SourceFeedJson INSTANCE = new SourceFeedJson();

  private SourceFeedJson()
  {
    super(SourceFeed.class, new Object[] { SourceFeedFeedAuthorJson.class, "author", "title" });
  }

  public static SourceFeedJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SourceFeedJson
 * JD-Core Version:    0.6.2
 */