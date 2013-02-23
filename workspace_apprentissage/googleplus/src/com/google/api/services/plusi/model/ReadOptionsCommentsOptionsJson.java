package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadOptionsCommentsOptionsJson extends EsJson<ReadOptionsCommentsOptions>
{
  static final ReadOptionsCommentsOptionsJson INSTANCE = new ReadOptionsCommentsOptionsJson();

  private ReadOptionsCommentsOptionsJson()
  {
    super(ReadOptionsCommentsOptions.class, new Object[] { "maxComments" });
  }

  public static ReadOptionsCommentsOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptionsCommentsOptionsJson
 * JD-Core Version:    0.6.2
 */