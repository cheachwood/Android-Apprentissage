package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LiveMapsPPEventJson extends EsJson<LiveMapsPPEvent>
{
  static final LiveMapsPPEventJson INSTANCE = new LiveMapsPPEventJson();

  private LiveMapsPPEventJson()
  {
    super(LiveMapsPPEvent.class, new Object[] { PlacePageLinkJson.class, "calendarLink", "category", "dateSectionTitle", "description", "formattedDate", "formattedDow", "formattedFullTime", "formattedTime", "fullDescription", LiveMapsProviderDataJson.class, "providerData", PlacePageLinkJson.class, "titleLink" });
  }

  public static LiveMapsPPEventJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LiveMapsPPEventJson
 * JD-Core Version:    0.6.2
 */