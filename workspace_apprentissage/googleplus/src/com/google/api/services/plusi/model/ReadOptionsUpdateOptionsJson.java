package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadOptionsUpdateOptionsJson extends EsJson<ReadOptionsUpdateOptions>
{
  static final ReadOptionsUpdateOptionsJson INSTANCE = new ReadOptionsUpdateOptionsJson();

  private ReadOptionsUpdateOptionsJson()
  {
    super(ReadOptionsUpdateOptions.class, new Object[] { "includeActivityId", "includeUpdate" });
  }

  public static ReadOptionsUpdateOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptionsUpdateOptionsJson
 * JD-Core Version:    0.6.2
 */