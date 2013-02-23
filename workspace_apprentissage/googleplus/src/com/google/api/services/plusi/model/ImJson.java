package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ImJson extends EsJson<Im>
{
  static final ImJson INSTANCE = new ImJson();

  private ImJson()
  {
    super(Im.class, new Object[] { "protocol", "value" });
  }

  public static ImJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ImJson
 * JD-Core Version:    0.6.2
 */