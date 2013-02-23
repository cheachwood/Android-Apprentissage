package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPhotoJson extends EsJson<PlusPhoto>
{
  static final PlusPhotoJson INSTANCE = new PlusPhotoJson();

  private PlusPhotoJson()
  {
    super(PlusPhoto.class, new Object[] { "albumId", "isVideo", "mediaType", "name", "onepickMediaId", "originalContentUrl", "originalLightboxPhotoId", "originalMediaContainerUrl", "originalMediaPlayerUrl", "ownerObfuscatedId", "photoId", ImageObjectJson.class, "thumbnail", "url" });
  }

  public static PlusPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotoJson
 * JD-Core Version:    0.6.2
 */