package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ShareboxSettings extends GenericJson
{
  public List<String> allowedDomainOrigin;
  public String defaultAcl;
  public List<ShareboxSettingsDefaultAcl> defaultAcls;
  public RenderedSharingRosters defaultSharingRosters;
  public Boolean invalidClientId;
  public Boolean isDriveUser;
  public String lastLocationDisplayType;
  public List<String> recentAcls;
  public Boolean seenDisableCommentsOob;
  public Boolean seenEmailConfirmationOob;
  public Boolean seenFirstPostOob;
  public Boolean seenIncludeLocation;
  public Boolean seenLockPostOob;
  public Boolean seenRestrictOob;
  public Boolean sendImplicitInvitesDefault;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ShareboxSettings
 * JD-Core Version:    0.6.2
 */