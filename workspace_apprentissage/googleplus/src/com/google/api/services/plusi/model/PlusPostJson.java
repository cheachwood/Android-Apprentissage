package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPostJson extends EsJson<PlusPost>
{
  static final PlusPostJson INSTANCE = new PlusPostJson();

  private PlusPostJson()
  {
    super(PlusPost.class, new Object[] { "id", "name", "url" });
  }

  public static PlusPostJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPostJson
 * JD-Core Version:    0.6.2
 */