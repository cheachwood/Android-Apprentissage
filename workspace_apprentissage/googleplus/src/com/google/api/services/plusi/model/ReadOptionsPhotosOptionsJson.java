package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadOptionsPhotosOptionsJson extends EsJson<ReadOptionsPhotosOptions>
{
  static final ReadOptionsPhotosOptionsJson INSTANCE = new ReadOptionsPhotosOptionsJson();

  private ReadOptionsPhotosOptionsJson()
  {
    super(ReadOptionsPhotosOptions.class, new Object[] { "includeComments", "includePlusOnes", "maxPhotos", "sortCriteria", "targetObfuscatedId", "targetedPhotoIds" });
  }

  public static ReadOptionsPhotosOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptionsPhotosOptionsJson
 * JD-Core Version:    0.6.2
 */