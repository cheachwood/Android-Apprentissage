package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AddressJson extends EsJson<Address>
{
  static final AddressJson INSTANCE = new AddressJson();

  private AddressJson()
  {
    super(Address.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static AddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressJson
 * JD-Core Version:    0.6.2
 */