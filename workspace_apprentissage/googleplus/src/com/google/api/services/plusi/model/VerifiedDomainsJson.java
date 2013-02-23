package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VerifiedDomainsJson extends EsJson<VerifiedDomains>
{
  static final VerifiedDomainsJson INSTANCE = new VerifiedDomainsJson();

  private VerifiedDomainsJson()
  {
    super(VerifiedDomains.class, new Object[] { DomainJson.class, "domain", MetadataJson.class, "metadata" });
  }

  public static VerifiedDomainsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VerifiedDomainsJson
 * JD-Core Version:    0.6.2
 */