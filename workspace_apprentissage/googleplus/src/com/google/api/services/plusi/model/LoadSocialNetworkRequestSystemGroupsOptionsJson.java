package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadSocialNetworkRequestSystemGroupsOptionsJson extends EsJson<LoadSocialNetworkRequestSystemGroupsOptions>
{
  static final LoadSocialNetworkRequestSystemGroupsOptionsJson INSTANCE = new LoadSocialNetworkRequestSystemGroupsOptionsJson();

  private LoadSocialNetworkRequestSystemGroupsOptionsJson()
  {
    super(LoadSocialNetworkRequestSystemGroupsOptions.class, new Object[] { "includeMemberCounts", "includeSystemGroups" });
  }

  public static LoadSocialNetworkRequestSystemGroupsOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkRequestSystemGroupsOptionsJson
 * JD-Core Version:    0.6.2
 */