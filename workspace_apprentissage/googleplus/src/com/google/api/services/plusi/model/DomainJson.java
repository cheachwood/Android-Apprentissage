package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DomainJson extends EsJson<Domain>
{
  static final DomainJson INSTANCE = new DomainJson();

  private DomainJson()
  {
    super(Domain.class, new Object[] { "name", "visible" });
  }

  public static DomainJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DomainJson
 * JD-Core Version:    0.6.2
 */