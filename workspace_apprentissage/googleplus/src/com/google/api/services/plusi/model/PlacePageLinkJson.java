package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlacePageLinkJson extends EsJson<PlacePageLink>
{
  static final PlacePageLinkJson INSTANCE = new PlacePageLinkJson();

  private PlacePageLinkJson()
  {
    super(PlacePageLink.class, new Object[] { ClickTrackingDataProtoJson.class, "clickTrackingData", "linkType", "provider", "redirect", "text", "url", "veId", "ved" });
  }

  public static PlacePageLinkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePageLinkJson
 * JD-Core Version:    0.6.2
 */