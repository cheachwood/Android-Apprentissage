package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadSocialNetworkResponseJson extends EsJson<LoadSocialNetworkResponse>
{
  static final LoadSocialNetworkResponseJson INSTANCE = new LoadSocialNetworkResponseJson();

  private LoadSocialNetworkResponseJson()
  {
    super(LoadSocialNetworkResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPersonListJson.class, "personList", DataSystemGroupsJson.class, "systemGroups", DataViewerCirclesJson.class, "viewerCircles" });
  }

  public static LoadSocialNetworkResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkResponseJson
 * JD-Core Version:    0.6.2
 */