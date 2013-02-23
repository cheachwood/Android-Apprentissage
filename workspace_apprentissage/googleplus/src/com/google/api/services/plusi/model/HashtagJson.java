package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HashtagJson extends EsJson<Hashtag>
{
  static final HashtagJson INSTANCE = new HashtagJson();

  private HashtagJson()
  {
    super(Hashtag.class, new Object[] { "searchText" });
  }

  public static HashtagJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HashtagJson
 * JD-Core Version:    0.6.2
 */