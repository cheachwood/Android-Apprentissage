package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RequestsPhotoOptionsJson extends EsJson<RequestsPhotoOptions>
{
  static final RequestsPhotoOptionsJson INSTANCE = new RequestsPhotoOptionsJson();

  private RequestsPhotoOptionsJson()
  {
    super(RequestsPhotoOptions.class, new Object[] { "contentFormat", "returnAlbumInfo", "returnAsbeUpdates", "returnComments", "returnDownloadability", "returnOwnerInfo", "returnPhotos", "returnPlusOnes", "returnShapes", "returnVideoUrls" });
  }

  public static RequestsPhotoOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RequestsPhotoOptionsJson
 * JD-Core Version:    0.6.2
 */