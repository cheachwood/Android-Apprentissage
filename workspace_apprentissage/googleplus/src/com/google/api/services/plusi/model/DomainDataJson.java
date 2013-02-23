package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DomainDataJson extends EsJson<DomainData>
{
  static final DomainDataJson INSTANCE = new DomainDataJson();

  private DomainDataJson()
  {
    super(DomainData.class, new Object[] { "displayName", "id", "name" });
  }

  public static DomainDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DomainDataJson
 * JD-Core Version:    0.6.2
 */