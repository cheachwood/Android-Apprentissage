package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class GeoPoint extends GenericJson
{
  public Long latitude;
  public Long longitude;
  public Metadata metadata;
  public String pointSource;
  public Boolean shouldRegeocode;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GeoPoint
 * JD-Core Version:    0.6.2
 */