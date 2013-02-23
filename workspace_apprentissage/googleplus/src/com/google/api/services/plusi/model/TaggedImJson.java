package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TaggedImJson extends EsJson<TaggedIm>
{
  static final TaggedImJson INSTANCE = new TaggedImJson();

  private TaggedImJson()
  {
    super(TaggedIm.class, new Object[] { MetadataJson.class, "metadata", "protocol", ContactTagJson.class, "tag", "value" });
  }

  public static TaggedImJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TaggedImJson
 * JD-Core Version:    0.6.2
 */