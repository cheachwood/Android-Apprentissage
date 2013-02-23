package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadSocialNetworkRequestJson extends EsJson<LoadSocialNetworkRequest>
{
  static final LoadSocialNetworkRequestJson INSTANCE = new LoadSocialNetworkRequestJson();

  private LoadSocialNetworkRequestJson()
  {
    super(LoadSocialNetworkRequest.class, new Object[] { LoadSocialNetworkRequestViewerCirclesOptionsJson.class, "circlesOptions", ApiaryFieldsJson.class, "commonFields", "enableTracing", LoadSocialNetworkRequestPersonListOptionsJson.class, "personListOptions", LoadSocialNetworkRequestSystemGroupsOptionsJson.class, "systemGroupsOptions" });
  }

  public static LoadSocialNetworkRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkRequestJson
 * JD-Core Version:    0.6.2
 */