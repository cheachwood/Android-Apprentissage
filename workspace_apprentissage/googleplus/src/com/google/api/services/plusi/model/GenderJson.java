package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GenderJson extends EsJson<Gender>
{
  static final GenderJson INSTANCE = new GenderJson();

  private GenderJson()
  {
    super(Gender.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static GenderJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GenderJson
 * JD-Core Version:    0.6.2
 */