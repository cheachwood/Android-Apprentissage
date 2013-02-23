package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileVisibilityEditJson extends EsJson<ProfileVisibilityEdit>
{
  static final ProfileVisibilityEditJson INSTANCE = new ProfileVisibilityEditJson();

  private ProfileVisibilityEditJson()
  {
    super(ProfileVisibilityEdit.class, new Object[] { "madeVisible", "setting" });
  }

  public static ProfileVisibilityEditJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileVisibilityEditJson
 * JD-Core Version:    0.6.2
 */