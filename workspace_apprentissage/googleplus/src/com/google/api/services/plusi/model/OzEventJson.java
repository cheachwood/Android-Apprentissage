package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OzEventJson extends EsJson<OzEvent>
{
  static final OzEventJson INSTANCE = new OzEventJson();

  private OzEventJson()
  {
    super(OzEvent.class, arrayOfObject);
  }

  public static OzEventJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OzEventJson
 * JD-Core Version:    0.6.2
 */