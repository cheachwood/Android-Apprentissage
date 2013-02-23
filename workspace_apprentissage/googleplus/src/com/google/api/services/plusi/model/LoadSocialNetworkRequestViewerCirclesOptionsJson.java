package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoadSocialNetworkRequestViewerCirclesOptionsJson extends EsJson<LoadSocialNetworkRequestViewerCirclesOptions>
{
  static final LoadSocialNetworkRequestViewerCirclesOptionsJson INSTANCE = new LoadSocialNetworkRequestViewerCirclesOptionsJson();

  private LoadSocialNetworkRequestViewerCirclesOptionsJson()
  {
    super(LoadSocialNetworkRequestViewerCirclesOptions.class, new Object[] { "includeCircles", "includeMemberCounts" });
  }

  public static LoadSocialNetworkRequestViewerCirclesOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkRequestViewerCirclesOptionsJson
 * JD-Core Version:    0.6.2
 */