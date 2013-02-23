package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CityblockProtoPanoJson extends EsJson<CityblockProtoPano>
{
  static final CityblockProtoPanoJson INSTANCE = new CityblockProtoPanoJson();

  private CityblockProtoPanoJson()
  {
    super(CityblockProtoPano.class, new Object[] { "latitudeE6", PlacePageLinkJson.class, "link", "longitudeE6", "panoId", "pitchDeg", "thumbnailUrl", "thumbnailUrls", "type", "yawDeg" });
  }

  public static CityblockProtoPanoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CityblockProtoPanoJson
 * JD-Core Version:    0.6.2
 */