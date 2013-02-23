package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosInAlbumResponseJson extends EsJson<PhotosInAlbumResponse>
{
  static final PhotosInAlbumResponseJson INSTANCE = new PhotosInAlbumResponseJson();

  private PhotosInAlbumResponseJson()
  {
    super(PhotosInAlbumResponse.class, new Object[] { DataAlbumJson.class, "album", TraceRecordsJson.class, "backendTrace", "errorCode", DataPhotoJson.class, "featuredPhoto", "isDownloadable", DataUserJson.class, "owner", DataPhotoJson.class, "photo" });
  }

  public static PhotosInAlbumResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosInAlbumResponseJson
 * JD-Core Version:    0.6.2
 */