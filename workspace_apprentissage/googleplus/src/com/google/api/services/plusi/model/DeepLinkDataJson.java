package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeepLinkDataJson extends EsJson<DeepLinkData>
{
  static final DeepLinkDataJson INSTANCE = new DeepLinkDataJson();

  private DeepLinkDataJson()
  {
    super(DeepLinkData.class, arrayOfObject);
  }

  public static DeepLinkDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeepLinkDataJson
 * JD-Core Version:    0.6.2
 */