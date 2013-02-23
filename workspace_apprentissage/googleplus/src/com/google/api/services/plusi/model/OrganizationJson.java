package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OrganizationJson extends EsJson<Organization>
{
  static final OrganizationJson INSTANCE = new OrganizationJson();

  private OrganizationJson()
  {
    super(Organization.class, new Object[] { "description", "imageUrl", "name", "url" });
  }

  public static OrganizationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OrganizationJson
 * JD-Core Version:    0.6.2
 */