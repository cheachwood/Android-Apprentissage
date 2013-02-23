package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SourceJson extends EsJson<Source>
{
  static final SourceJson INSTANCE = new SourceJson();

  private SourceJson()
  {
    super(Source.class, new Object[] { SourceIdJson.class, "id", "name", "type", "url" });
  }

  public static SourceJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SourceJson
 * JD-Core Version:    0.6.2
 */