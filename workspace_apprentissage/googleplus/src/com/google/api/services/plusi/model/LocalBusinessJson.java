package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalBusinessJson extends EsJson<LocalBusiness>
{
  static final LocalBusinessJson INSTANCE = new LocalBusinessJson();

  private LocalBusinessJson()
  {
    super(LocalBusiness.class, new Object[] { EmbedsPostalAddressJson.class, "address", "description", "imageUrl", "name", "url" });
  }

  public static LocalBusinessJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalBusinessJson
 * JD-Core Version:    0.6.2
 */