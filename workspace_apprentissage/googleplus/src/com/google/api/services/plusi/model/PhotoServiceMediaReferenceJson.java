package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoServiceMediaReferenceJson extends EsJson<PhotoServiceMediaReference>
{
  static final PhotoServiceMediaReferenceJson INSTANCE = new PhotoServiceMediaReferenceJson();

  private PhotoServiceMediaReferenceJson()
  {
    super(PhotoServiceMediaReference.class, new Object[] { "clientAssignedUniqueId", "imageData", "imageStatus", "mediaType", PhotoServiceMediaReferencePhotoJson.class, "sourcePhoto" });
  }

  public static PhotoServiceMediaReferenceJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoServiceMediaReferenceJson
 * JD-Core Version:    0.6.2
 */