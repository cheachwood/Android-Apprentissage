package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SourceFeedFeedAuthorJson extends EsJson<SourceFeedFeedAuthor>
{
  static final SourceFeedFeedAuthorJson INSTANCE = new SourceFeedFeedAuthorJson();

  private SourceFeedFeedAuthorJson()
  {
    super(SourceFeedFeedAuthor.class, new Object[] { "name", "uri" });
  }

  public static SourceFeedFeedAuthorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SourceFeedFeedAuthorJson
 * JD-Core Version:    0.6.2
 */