package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CityblockProtoJson extends EsJson<CityblockProto>
{
  static final CityblockProtoJson INSTANCE = new CityblockProtoJson();

  private CityblockProtoJson()
  {
    super(CityblockProto.class, new Object[] { PlacePageLinkJson.class, "link", CityblockProtoPanoJson.class, "pano", "thumbnailUrl" });
  }

  public static CityblockProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CityblockProtoJson
 * JD-Core Version:    0.6.2
 */