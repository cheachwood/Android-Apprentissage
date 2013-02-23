package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TaggedAddressJson extends EsJson<TaggedAddress>
{
  static final TaggedAddressJson INSTANCE = new TaggedAddressJson();

  private TaggedAddressJson()
  {
    super(TaggedAddress.class, new Object[] { MetadataJson.class, "metadata", ContactTagJson.class, "tag", "value" });
  }

  public static TaggedAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TaggedAddressJson
 * JD-Core Version:    0.6.2
 */