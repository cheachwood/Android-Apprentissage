package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserPhotoAlbumsRequestJson extends EsJson<UserPhotoAlbumsRequest>
{
  static final UserPhotoAlbumsRequestJson INSTANCE = new UserPhotoAlbumsRequestJson();

  private UserPhotoAlbumsRequestJson()
  {
    super(UserPhotoAlbumsRequest.class, new Object[] { "albumTypes", ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxPreviewCount", "maxResults", "offset", "ownerId", "sharedAlbumsOnly" });
  }

  public static UserPhotoAlbumsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserPhotoAlbumsRequestJson
 * JD-Core Version:    0.6.2
 */