package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ScottyInfoJson extends EsJson<ScottyInfo>
{
  static final ScottyInfoJson INSTANCE = new ScottyInfoJson();

  private ScottyInfoJson()
  {
    super(ScottyInfo.class, new Object[] { CustomAgentDataJson.class, "customAgentData" });
  }

  public static ScottyInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ScottyInfoJson
 * JD-Core Version:    0.6.2
 */