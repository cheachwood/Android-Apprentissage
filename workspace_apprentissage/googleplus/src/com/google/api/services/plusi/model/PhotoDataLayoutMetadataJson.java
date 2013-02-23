package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataLayoutMetadataJson extends EsJson<PhotoDataLayoutMetadata>
{
  static final PhotoDataLayoutMetadataJson INSTANCE = new PhotoDataLayoutMetadataJson();

  private PhotoDataLayoutMetadataJson()
  {
    super(PhotoDataLayoutMetadata.class, new Object[] { "format" });
  }

  public static PhotoDataLayoutMetadataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataLayoutMetadataJson
 * JD-Core Version:    0.6.2
 */