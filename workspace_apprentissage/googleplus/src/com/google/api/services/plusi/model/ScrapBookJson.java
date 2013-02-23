package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScrapBookJson extends EsJson<ScrapBook>
{
  static final ScrapBookJson INSTANCE = new ScrapBookJson();

  private ScrapBookJson()
  {
    super(ScrapBook.class, arrayOfObject);
  }

  public static ScrapBookJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScrapBookJson
 * JD-Core Version:    0.6.2
 */