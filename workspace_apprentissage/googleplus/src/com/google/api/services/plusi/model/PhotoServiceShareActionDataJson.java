package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoServiceShareActionDataJson extends EsJson<PhotoServiceShareActionData>
{
  static final PhotoServiceShareActionDataJson INSTANCE = new PhotoServiceShareActionDataJson();

  private PhotoServiceShareActionDataJson()
  {
    super(PhotoServiceShareActionData.class, new Object[] { "albumTitle", "isAlbumReshare", "isFullAlbumShare", PhotoServiceMediaReferenceJson.class, "mediaRef", PhotoServiceShareActionDataAlbumJson.class, "targetAlbum" });
  }

  public static PhotoServiceShareActionDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoServiceShareActionDataJson
 * JD-Core Version:    0.6.2
 */