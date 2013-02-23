package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DebugJson extends EsJson<Debug>
{
  static final DebugJson INSTANCE = new DebugJson();

  private DebugJson()
  {
    super(Debug.class, new Object[] { "key", "value" });
  }

  public static DebugJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DebugJson
 * JD-Core Version:    0.6.2
 */