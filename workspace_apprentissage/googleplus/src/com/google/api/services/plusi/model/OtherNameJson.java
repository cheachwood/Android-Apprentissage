package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OtherNameJson extends EsJson<OtherName>
{
  static final OtherNameJson INSTANCE = new OtherNameJson();

  private OtherNameJson()
  {
    super(OtherName.class, new Object[] { "value" });
  }

  public static OtherNameJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OtherNameJson
 * JD-Core Version:    0.6.2
 */