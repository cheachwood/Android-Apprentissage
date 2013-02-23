package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlacePageCheckinJson extends EsJson<PlacePageCheckin>
{
  static final PlacePageCheckinJson INSTANCE = new PlacePageCheckinJson();

  private PlacePageCheckinJson()
  {
    super(PlacePageCheckin.class, arrayOfObject);
  }

  public static PlacePageCheckinJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePageCheckinJson
 * JD-Core Version:    0.6.2
 */