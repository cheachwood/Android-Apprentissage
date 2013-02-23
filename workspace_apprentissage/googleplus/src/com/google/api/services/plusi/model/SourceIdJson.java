package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SourceIdJson extends EsJson<SourceId>
{
  static final SourceIdJson INSTANCE = new SourceIdJson();

  private SourceIdJson()
  {
    super(SourceId.class, new Object[] { "value" });
  }

  public static SourceIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SourceIdJson
 * JD-Core Version:    0.6.2
 */