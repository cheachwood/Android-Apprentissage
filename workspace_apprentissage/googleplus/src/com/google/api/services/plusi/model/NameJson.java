package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NameJson extends EsJson<Name>
{
  static final NameJson INSTANCE = new NameJson();

  private NameJson()
  {
    super(Name.class, arrayOfObject);
  }

  public static NameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NameJson
 * JD-Core Version:    0.6.2
 */