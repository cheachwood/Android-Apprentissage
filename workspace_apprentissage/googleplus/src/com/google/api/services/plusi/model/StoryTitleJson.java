package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StoryTitleJson extends EsJson<StoryTitle>
{
  static final StoryTitleJson INSTANCE = new StoryTitleJson();

  private StoryTitleJson()
  {
    super(StoryTitle.class, new Object[] { "title" });
  }

  public static StoryTitleJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StoryTitleJson
 * JD-Core Version:    0.6.2
 */