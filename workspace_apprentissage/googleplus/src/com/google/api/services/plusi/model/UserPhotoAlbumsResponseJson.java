package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserPhotoAlbumsResponseJson extends EsJson<UserPhotoAlbumsResponse>
{
  static final UserPhotoAlbumsResponseJson INSTANCE = new UserPhotoAlbumsResponseJson();

  private UserPhotoAlbumsResponseJson()
  {
    super(UserPhotoAlbumsResponse.class, new Object[] { DataAlbumJson.class, "aggregateAlbum", TraceRecordsJson.class, "backendTrace", DataAlbumJson.class, "nonAggregateAlbum", "offset", DataUserJson.class, "owner" });
  }

  public static UserPhotoAlbumsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserPhotoAlbumsResponseJson
 * JD-Core Version:    0.6.2
 */