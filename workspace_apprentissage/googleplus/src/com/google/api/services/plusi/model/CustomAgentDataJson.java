package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CustomAgentDataJson extends EsJson<CustomAgentData>
{
  static final CustomAgentDataJson INSTANCE = new CustomAgentDataJson();

  private CustomAgentDataJson()
  {
    super(CustomAgentData.class, arrayOfObject);
  }

  public static CustomAgentDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CustomAgentDataJson
 * JD-Core Version:    0.6.2
 */