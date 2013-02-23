package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataAlbumJson extends EsJson<DataAlbum>
{
  static final DataAlbumJson INSTANCE = new DataAlbumJson();

  private DataAlbumJson()
  {
    super(DataAlbum.class, arrayOfObject);
  }

  public static DataAlbumJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataAlbumJson
 * JD-Core Version:    0.6.2
 */