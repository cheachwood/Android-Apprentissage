package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostClientLogsResponseJson extends EsJson<PostClientLogsResponse>
{
  static final PostClientLogsResponseJson INSTANCE = new PostClientLogsResponseJson();

  private PostClientLogsResponseJson()
  {
    super(PostClientLogsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static PostClientLogsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostClientLogsResponseJson
 * JD-Core Version:    0.6.2
 */