package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LinksJson extends EsJson<Links>
{
  static final LinksJson INSTANCE = new LinksJson();

  private LinksJson()
  {
    super(Links.class, new Object[] { MetadataJson.class, "defaultMetadata", ProfilesLinkJson.class, "link", MetadataJson.class, "metadata" });
  }

  public static LinksJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinksJson
 * JD-Core Version:    0.6.2
 */