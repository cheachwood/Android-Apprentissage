package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileStateJson extends EsJson<ProfileState>
{
  static final ProfileStateJson INSTANCE = new ProfileStateJson();

  private ProfileStateJson()
  {
    super(ProfileState.class, arrayOfObject);
  }

  public static ProfileStateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileStateJson
 * JD-Core Version:    0.6.2
 */