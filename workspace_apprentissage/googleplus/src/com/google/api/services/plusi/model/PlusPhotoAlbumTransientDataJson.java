package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPhotoAlbumTransientDataJson extends EsJson<PlusPhotoAlbumTransientData>
{
  static final PlusPhotoAlbumTransientDataJson INSTANCE = new PlusPhotoAlbumTransientDataJson();

  private PlusPhotoAlbumTransientDataJson()
  {
    super(PlusPhotoAlbumTransientData.class, new Object[] { "isAlbumReshare", "isFullAlbumShare" });
  }

  public static PlusPhotoAlbumTransientDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotoAlbumTransientDataJson
 * JD-Core Version:    0.6.2
 */