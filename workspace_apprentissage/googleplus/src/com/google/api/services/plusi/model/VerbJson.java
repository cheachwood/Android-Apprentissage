package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VerbJson extends EsJson<Verb>
{
  static final VerbJson INSTANCE = new VerbJson();

  private VerbJson()
  {
    super(Verb.class, new Object[] { VerbIdJson.class, "id", "type" });
  }

  public static VerbJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VerbJson
 * JD-Core Version:    0.6.2
 */