package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TaggedEmailJson extends EsJson<TaggedEmail>
{
  static final TaggedEmailJson INSTANCE = new TaggedEmailJson();

  private TaggedEmailJson()
  {
    super(TaggedEmail.class, new Object[] { MetadataJson.class, "metadata", ContactTagJson.class, "tag", "value", "verified" });
  }

  public static TaggedEmailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TaggedEmailJson
 * JD-Core Version:    0.6.2
 */