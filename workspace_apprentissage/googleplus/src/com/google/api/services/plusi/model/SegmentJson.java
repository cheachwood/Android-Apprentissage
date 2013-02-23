package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SegmentJson extends EsJson<Segment>
{
  static final SegmentJson INSTANCE = new SegmentJson();

  private SegmentJson()
  {
    super(Segment.class, new Object[] { FormattingJson.class, "formatting", HashtagJson.class, "hashtag", LinkJson.class, "link", "text", "type", UserRefJson.class, "user" });
  }

  public static SegmentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SegmentJson
 * JD-Core Version:    0.6.2
 */