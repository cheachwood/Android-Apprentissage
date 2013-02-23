package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GadgetsDataJson extends EsJson<GadgetsData>
{
  static final GadgetsDataJson INSTANCE = new GadgetsDataJson();

  private GadgetsDataJson()
  {
    super(GadgetsData.class, new Object[] { "anchorText", "anchorUrl", "gadgetId", "gadgetImageUrl", "gadgetMessage", "gadgetMessageImageUrl", "gadgetMessageTitle", "gadgetName", "gadgetThumbnailUrl", "navParams", "type", "userAnnotation" });
  }

  public static GadgetsDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GadgetsDataJson
 * JD-Core Version:    0.6.2
 */