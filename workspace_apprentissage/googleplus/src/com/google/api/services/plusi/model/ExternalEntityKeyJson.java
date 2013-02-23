package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ExternalEntityKeyJson extends EsJson<ExternalEntityKey>
{
  static final ExternalEntityKeyJson INSTANCE = new ExternalEntityKeyJson();

  private ExternalEntityKeyJson()
  {
    super(ExternalEntityKey.class, new Object[] { "domain", "id" });
  }

  public static ExternalEntityKeyJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ExternalEntityKeyJson
 * JD-Core Version:    0.6.2
 */