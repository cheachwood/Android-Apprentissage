package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostActivityResponseJson extends EsJson<PostActivityResponse>
{
  static final PostActivityResponseJson INSTANCE = new PostActivityResponseJson();

  private PostActivityResponseJson()
  {
    super(PostActivityResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", ShareboxSettingsJson.class, "shareboxSettings", StreamJson.class, "stream" });
  }

  public static PostActivityResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostActivityResponseJson
 * JD-Core Version:    0.6.2
 */