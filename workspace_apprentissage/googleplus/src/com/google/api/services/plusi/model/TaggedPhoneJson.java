package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TaggedPhoneJson extends EsJson<TaggedPhone>
{
  static final TaggedPhoneJson INSTANCE = new TaggedPhoneJson();

  private TaggedPhoneJson()
  {
    super(TaggedPhone.class, new Object[] { MetadataJson.class, "metadata", ContactTagJson.class, "tag", "type", "value" });
  }

  public static TaggedPhoneJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TaggedPhoneJson
 * JD-Core Version:    0.6.2
 */