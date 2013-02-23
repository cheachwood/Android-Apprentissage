package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataJson extends EsJson<PhotoData>
{
  static final PhotoDataJson INSTANCE = new PhotoDataJson();

  private PhotoDataJson()
  {
    super(PhotoData.class, new Object[] { PhotoDataActivityOnAlbumJson.class, "activityOnAlbum", PhotoDataActivityOnPhotoJson.class, "activityOnPhoto", PhotoDataChangedProfilePhotoJson.class, "changedProfilePhoto", PhotoDataLayoutMetadataJson.class, "layoutMetadata", PhotoDataPeopleInAlbumJson.class, "peopleInAlbum", PhotoDataPeopleInAlbumJson.class, "peopleInAlbumAnnotation", PhotoDataPhotoShareByOwnerJson.class, "photoShareByOwner", PhotoDataTaggedInPhotoJson.class, "taggedInPhoto", PhotoDataTaggerOfPhotoJson.class, "taggerOfPhoto" });
  }

  public static PhotoDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataJson
 * JD-Core Version:    0.6.2
 */