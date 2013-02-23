package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewportDataJson extends EsJson<ViewportData>
{
  static final ViewportDataJson INSTANCE = new ViewportDataJson();

  private ViewportDataJson()
  {
    super(ViewportData.class, new Object[] { "lat", "lng", "spanLat", "spanLng" });
  }

  public static ViewportDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewportDataJson
 * JD-Core Version:    0.6.2
 */