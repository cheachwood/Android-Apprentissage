package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VerbIdJson extends EsJson<VerbId>
{
  static final VerbIdJson INSTANCE = new VerbIdJson();

  private VerbIdJson()
  {
    super(VerbId.class, new Object[] { "value" });
  }

  public static VerbIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VerbIdJson
 * JD-Core Version:    0.6.2
 */