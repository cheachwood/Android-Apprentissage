package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataVideoThumbnailJson extends EsJson<DataVideoThumbnail>
{
  static final DataVideoThumbnailJson INSTANCE = new DataVideoThumbnailJson();

  private DataVideoThumbnailJson()
  {
    super(DataVideoThumbnail.class, arrayOfObject);
  }

  public static DataVideoThumbnailJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataVideoThumbnailJson
 * JD-Core Version:    0.6.2
 */