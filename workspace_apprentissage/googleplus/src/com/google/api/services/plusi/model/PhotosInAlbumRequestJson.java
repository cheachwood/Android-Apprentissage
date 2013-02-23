package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosInAlbumRequestJson extends EsJson<PhotosInAlbumRequest>
{
  static final PhotosInAlbumRequestJson INSTANCE = new PhotosInAlbumRequestJson();

  private PhotosInAlbumRequestJson()
  {
    super(PhotosInAlbumRequest.class, new Object[] { "authkey", "collectionId", ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxResults", "offset", "ownerId", RequestsPhotoOptionsJson.class, "photoOptions", "photosSortOrder" });
  }

  public static PhotosInAlbumRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosInAlbumRequestJson
 * JD-Core Version:    0.6.2
 */