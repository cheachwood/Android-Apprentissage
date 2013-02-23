package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmbedsPostalAddressJson extends EsJson<EmbedsPostalAddress>
{
  static final EmbedsPostalAddressJson INSTANCE = new EmbedsPostalAddressJson();

  private EmbedsPostalAddressJson()
  {
    super(EmbedsPostalAddress.class, new Object[] { "addressCountry", "addressLocality", "addressRegion", "name", "postOfficeBoxNumber", "postalCode", "streetAddress", "url" });
  }

  public static EmbedsPostalAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmbedsPostalAddressJson
 * JD-Core Version:    0.6.2
 */