package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedPhotoJson extends EsJson<LoggedPhoto>
{
  static final LoggedPhotoJson INSTANCE = new LoggedPhotoJson();

  private LoggedPhotoJson()
  {
    super(LoggedPhoto.class, arrayOfObject);
  }

  public static LoggedPhotoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedPhotoJson
 * JD-Core Version:    0.6.2
 */