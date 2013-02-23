package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileEditJson extends EsJson<ProfileEdit>
{
  static final ProfileEditJson INSTANCE = new ProfileEditJson();

  private ProfileEditJson()
  {
    super(ProfileEdit.class, new Object[] { "coverPhotoOffset", "coverPhotoOwnerType", "currentLocation", "education", "employment", "familyName", "fullBleedPhotoId", "givenName", "scrapbookLayout", ProfileVisibilityEditJson.class, "visibility" });
  }

  public static ProfileEditJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileEditJson
 * JD-Core Version:    0.6.2
 */