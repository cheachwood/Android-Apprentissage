package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfilesLinkJson extends EsJson<ProfilesLink>
{
  static final ProfilesLinkJson INSTANCE = new ProfilesLinkJson();

  private ProfilesLinkJson()
  {
    super(ProfilesLink.class, new Object[] { "faviconImgUrl", "isVerified", "label", MetadataJson.class, "metadata", "normalizedUri", "rel", "type", "url", VerificationJson.class, "verification" });
  }

  public static ProfilesLinkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfilesLinkJson
 * JD-Core Version:    0.6.2
 */