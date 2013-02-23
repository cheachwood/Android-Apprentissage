package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadSocialNetworkRequestPersonListOptionsJson extends EsJson<LoadSocialNetworkRequestPersonListOptions>
{
  static final LoadSocialNetworkRequestPersonListOptionsJson INSTANCE = new LoadSocialNetworkRequestPersonListOptionsJson();

  private LoadSocialNetworkRequestPersonListOptionsJson()
  {
    super(LoadSocialNetworkRequestPersonListOptions.class, new Object[] { "includeExtendedProfileInfo", "includePeople", "maxPeople", "profileTypesToFilter", DataSyncStateTokenJson.class, "syncStateToken" });
  }

  public static LoadSocialNetworkRequestPersonListOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkRequestPersonListOptionsJson
 * JD-Core Version:    0.6.2
 */