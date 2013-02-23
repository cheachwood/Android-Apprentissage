package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ContactInfo extends GenericJson
{
  public List<Address> address;
  public List<String> deprecatedAddress;
  public List<Email> email;
  public List<String> fax;
  public GeoPoint geoPoint;
  public List<Im> im;
  public Metadata metadata;
  public List<String> mobile;
  public List<String> pager;
  public List<String> phone;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ContactInfo
 * JD-Core Version:    0.6.2
 */