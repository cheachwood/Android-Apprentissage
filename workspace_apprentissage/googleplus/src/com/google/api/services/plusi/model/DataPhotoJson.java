package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPhotoJson extends EsJson<DataPhoto>
{
  static final DataPhotoJson INSTANCE = new DataPhotoJson();

  private DataPhotoJson()
  {
    super(DataPhoto.class, arrayOfObject);
  }

  public static DataPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPhotoJson
 * JD-Core Version:    0.6.2
 */