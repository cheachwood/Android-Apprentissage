package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ShareboxSettingsJson extends EsJson<ShareboxSettings>
{
  static final ShareboxSettingsJson INSTANCE = new ShareboxSettingsJson();

  private ShareboxSettingsJson()
  {
    super(ShareboxSettings.class, new Object[] { "allowedDomainOrigin", "defaultAcl", ShareboxSettingsDefaultAclJson.class, "defaultAcls", RenderedSharingRostersJson.class, "defaultSharingRosters", "invalidClientId", "isDriveUser", "lastLocationDisplayType", "recentAcls", "seenDisableCommentsOob", "seenEmailConfirmationOob", "seenFirstPostOob", "seenIncludeLocation", "seenLockPostOob", "seenRestrictOob", "sendImplicitInvitesDefault" });
  }

  public static ShareboxSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ShareboxSettingsJson
 * JD-Core Version:    0.6.2
 */