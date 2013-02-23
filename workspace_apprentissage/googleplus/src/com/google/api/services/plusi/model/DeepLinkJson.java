package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeepLinkJson extends EsJson<DeepLink>
{
  static final DeepLinkJson INSTANCE = new DeepLinkJson();

  private DeepLinkJson()
  {
    super(DeepLink.class, new Object[] { DeepLinkDataJson.class, "deepLink", "deepLinkLabel", "label", "renderedLabel" });
  }

  public static DeepLinkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeepLinkJson
 * JD-Core Version:    0.6.2
 */