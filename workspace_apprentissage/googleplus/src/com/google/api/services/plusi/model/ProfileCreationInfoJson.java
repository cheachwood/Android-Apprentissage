package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileCreationInfoJson extends EsJson<ProfileCreationInfo>
{
  static final ProfileCreationInfoJson INSTANCE = new ProfileCreationInfoJson();

  private ProfileCreationInfoJson()
  {
    super(ProfileCreationInfo.class, new Object[] { "plusPageType", "profileType" });
  }

  public static ProfileCreationInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileCreationInfoJson
 * JD-Core Version:    0.6.2
 */