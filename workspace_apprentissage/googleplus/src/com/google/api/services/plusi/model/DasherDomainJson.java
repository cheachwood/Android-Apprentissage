package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DasherDomainJson extends EsJson<DasherDomain>
{
  static final DasherDomainJson INSTANCE = new DasherDomainJson();

  private DasherDomainJson()
  {
    super(DasherDomain.class, new Object[] { "displayName", "domainName", "obfuscatedId" });
  }

  public static DasherDomainJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DasherDomainJson
 * JD-Core Version:    0.6.2
 */