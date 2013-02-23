package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ContactInfoJson extends EsJson<ContactInfo>
{
  static final ContactInfoJson INSTANCE = new ContactInfoJson();

  private ContactInfoJson()
  {
    super(ContactInfo.class, new Object[] { AddressJson.class, "address", "deprecatedAddress", EmailJson.class, "email", "fax", GeoPointJson.class, "geoPoint", ImJson.class, "im", MetadataJson.class, "metadata", "mobile", "pager", "phone" });
  }

  public static ContactInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ContactInfoJson
 * JD-Core Version:    0.6.2
 */