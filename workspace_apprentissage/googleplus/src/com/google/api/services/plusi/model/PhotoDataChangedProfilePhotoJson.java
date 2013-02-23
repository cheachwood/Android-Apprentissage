package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataChangedProfilePhotoJson extends EsJson<PhotoDataChangedProfilePhoto>
{
  static final PhotoDataChangedProfilePhotoJson INSTANCE = new PhotoDataChangedProfilePhotoJson();

  private PhotoDataChangedProfilePhotoJson()
  {
    super(PhotoDataChangedProfilePhoto.class, new Object[] { PhotoDataGenderJson.class, "gender", CommonPersonJson.class, "person", "profilePhotoUrl" });
  }

  public static PhotoDataChangedProfilePhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataChangedProfilePhotoJson
 * JD-Core Version:    0.6.2
 */