package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LiveMapsProviderDataJson extends EsJson<LiveMapsProviderData>
{
  static final LiveMapsProviderDataJson INSTANCE = new LiveMapsProviderDataJson();

  private LiveMapsProviderDataJson()
  {
    super(LiveMapsProviderData.class, new Object[] { "description", "domainUrl", PlacePageLinkJson.class, "provider" });
  }

  public static LiveMapsProviderDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LiveMapsProviderDataJson
 * JD-Core Version:    0.6.2
 */