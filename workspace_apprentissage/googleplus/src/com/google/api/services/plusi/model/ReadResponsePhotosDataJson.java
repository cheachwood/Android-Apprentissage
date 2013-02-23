package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadResponsePhotosDataJson extends EsJson<ReadResponsePhotosData>
{
  static final ReadResponsePhotosDataJson INSTANCE = new ReadResponsePhotosDataJson();

  private ReadResponsePhotosDataJson()
  {
    super(ReadResponsePhotosData.class, new Object[] { EmbedsPersonJson.class, "person", DataPhotoJson.class, "photos", "sortType" });
  }

  public static ReadResponsePhotosDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadResponsePhotosDataJson
 * JD-Core Version:    0.6.2
 */