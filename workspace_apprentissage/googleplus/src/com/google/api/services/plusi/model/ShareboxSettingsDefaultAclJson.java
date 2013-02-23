package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ShareboxSettingsDefaultAclJson extends EsJson<ShareboxSettingsDefaultAcl>
{
  static final ShareboxSettingsDefaultAclJson INSTANCE = new ShareboxSettingsDefaultAclJson();

  private ShareboxSettingsDefaultAclJson()
  {
    super(ShareboxSettingsDefaultAcl.class, new Object[] { "acl", "apiMode" });
  }

  public static ShareboxSettingsDefaultAclJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ShareboxSettingsDefaultAclJson
 * JD-Core Version:    0.6.2
 */