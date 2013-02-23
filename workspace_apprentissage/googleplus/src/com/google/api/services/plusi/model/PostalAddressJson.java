package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostalAddressJson extends EsJson<PostalAddress>
{
  static final PostalAddressJson INSTANCE = new PostalAddressJson();

  private PostalAddressJson()
  {
    super(PostalAddress.class, new Object[] { "addressLine", "administrativeAreaName", "countryName", "countryNameCode", "dependentLocalityName", "dependentThoroughfareLeadingType", "dependentThoroughfareName", "dependentThoroughfarePostDirection", "dependentThoroughfarePreDirection", "dependentThoroughfareTrailingType", "dependentThoroughfaresConnector", "dependentThoroughfaresIndicator", "dependentThoroughfaresType", "firmName", "languageCode", "localityName", "postBoxNumber", "postalCodeNumber", "postalCodeNumberExtension", "premiseName", "recipientName", "sortingCode", "subAdministrativeAreaName", "subPremiseName", "thoroughfareLeadingType", "thoroughfareName", "thoroughfareNumber", "thoroughfarePostDirection", "thoroughfarePreDirection", "thoroughfareTrailingType" });
  }

  public static PostalAddressJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostalAddressJson
 * JD-Core Version:    0.6.2
 */