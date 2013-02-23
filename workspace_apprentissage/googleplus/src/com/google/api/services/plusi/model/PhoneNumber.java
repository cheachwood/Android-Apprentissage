package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.math.BigInteger;

public final class PhoneNumber extends GenericJson
{
  public Integer countryCode;
  public String countryCodeSource;
  public String extension;
  public Boolean italianLeadingZero;
  public BigInteger nationalNumber;
  public String preferredDomesticCarrierCode;
  public String rawInput;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhoneNumber
 * JD-Core Version:    0.6.2
 */