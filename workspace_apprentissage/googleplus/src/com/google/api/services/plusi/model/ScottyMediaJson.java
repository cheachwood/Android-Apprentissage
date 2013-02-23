package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScottyMediaJson extends EsJson<ScottyMedia>
{
  static final ScottyMediaJson INSTANCE = new ScottyMediaJson();

  private ScottyMediaJson()
  {
    super(ScottyMedia.class, new Object[] { CustomAgentDataJson.class, "customAgentData" });
  }

  public static ScottyMediaJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScottyMediaJson
 * JD-Core Version:    0.6.2
 */