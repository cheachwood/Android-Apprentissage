package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostClientLogsRequestJson extends EsJson<PostClientLogsRequest>
{
  static final PostClientLogsRequestJson INSTANCE = new PostClientLogsRequestJson();

  private PostClientLogsRequestJson()
  {
    super(PostClientLogsRequest.class, new Object[] { ClientOzExtensionJson.class, "clientLog", ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static PostClientLogsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostClientLogsRequestJson
 * JD-Core Version:    0.6.2
 */