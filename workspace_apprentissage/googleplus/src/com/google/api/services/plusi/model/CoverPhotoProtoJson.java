package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CoverPhotoProtoJson extends EsJson<CoverPhotoProto>
{
  static final CoverPhotoProtoJson INSTANCE = new CoverPhotoProtoJson();

  private CoverPhotoProtoJson()
  {
    super(CoverPhotoProto.class, new Object[] { MediaProtoJson.class, "coverPhoto", MediaProtoThumbnailJson.class, "logoAttribution", PlacePageLinkJson.class, "textAttribution" });
  }

  public static CoverPhotoProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CoverPhotoProtoJson
 * JD-Core Version:    0.6.2
 */