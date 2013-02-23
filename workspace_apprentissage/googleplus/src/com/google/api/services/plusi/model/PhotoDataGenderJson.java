package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotoDataGenderJson extends EsJson<PhotoDataGender>
{
  static final PhotoDataGenderJson INSTANCE = new PhotoDataGenderJson();

  private PhotoDataGenderJson()
  {
    super(PhotoDataGender.class, new Object[] { "type" });
  }

  public static PhotoDataGenderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoDataGenderJson
 * JD-Core Version:    0.6.2
 */