package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoVideoProtoJson extends EsJson<PhotoVideoProto>
{
  static final PhotoVideoProtoJson INSTANCE = new PhotoVideoProtoJson();

  private PhotoVideoProtoJson()
  {
    super(PhotoVideoProto.class, new Object[] { MediaProtoJson.class, "media", PlacePageLinkJson.class, "morePhotos", PlacePageLinkJson.class, "moreVideos", NavbarProtoJson.class, "navbar", "photoSize", ResultsRangeProtoJson.class, "resultsRange", "totalNumPhotos", "totalNumVideos" });
  }

  public static PhotoVideoProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoVideoProtoJson
 * JD-Core Version:    0.6.2
 */