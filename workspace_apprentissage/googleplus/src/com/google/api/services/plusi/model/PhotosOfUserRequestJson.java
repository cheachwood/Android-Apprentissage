package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosOfUserRequestJson extends EsJson<PhotosOfUserRequest>
{
  static final PhotosOfUserRequestJson INSTANCE = new PhotosOfUserRequestJson();

  private PhotosOfUserRequestJson()
  {
    super(PhotosOfUserRequest.class, new Object[] { "approvedResumeToken", ApiaryFieldsJson.class, "commonFields", "enableTracing", "maxResults", "ownerId", RequestsPhotoOptionsJson.class, "photoOptions", "suggestedResumeToken", "unapprovedResumeToken" });
  }

  public static PhotosOfUserRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosOfUserRequestJson
 * JD-Core Version:    0.6.2
 */