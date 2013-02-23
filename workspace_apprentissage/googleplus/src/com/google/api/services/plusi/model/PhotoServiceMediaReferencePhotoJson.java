package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoServiceMediaReferencePhotoJson extends EsJson<PhotoServiceMediaReferencePhoto>
{
  static final PhotoServiceMediaReferencePhotoJson INSTANCE = new PhotoServiceMediaReferencePhotoJson();

  private PhotoServiceMediaReferencePhotoJson()
  {
    super(PhotoServiceMediaReferencePhoto.class, new Object[] { "obfuscatedOwnerId", "photoId" });
  }

  public static PhotoServiceMediaReferencePhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoServiceMediaReferencePhotoJson
 * JD-Core Version:    0.6.2
 */