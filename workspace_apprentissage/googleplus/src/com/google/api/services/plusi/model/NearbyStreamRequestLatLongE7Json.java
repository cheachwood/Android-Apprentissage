package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NearbyStreamRequestLatLongE7Json extends EsJson<NearbyStreamRequestLatLongE7>
{
  static final NearbyStreamRequestLatLongE7Json INSTANCE = new NearbyStreamRequestLatLongE7Json();

  private NearbyStreamRequestLatLongE7Json()
  {
    super(NearbyStreamRequestLatLongE7.class, new Object[] { "latitude", "longitude" });
  }

  public static NearbyStreamRequestLatLongE7Json getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyStreamRequestLatLongE7Json
 * JD-Core Version:    0.6.2
 */