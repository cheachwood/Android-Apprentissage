package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StructuredAddressProtoJson extends EsJson<StructuredAddressProto>
{
  static final StructuredAddressProtoJson INSTANCE = new StructuredAddressProtoJson();

  private StructuredAddressProtoJson()
  {
    super(StructuredAddressProto.class, new Object[] { AddressProtoJson.class, "address", PostalAddressJson.class, "postalAddress", "precision", "streetAddress" });
  }

  public static StructuredAddressProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StructuredAddressProtoJson
 * JD-Core Version:    0.6.2
 */