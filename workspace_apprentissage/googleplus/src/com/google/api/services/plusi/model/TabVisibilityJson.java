package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TabVisibilityJson extends EsJson<TabVisibility>
{
  static final TabVisibilityJson INSTANCE = new TabVisibilityJson();

  private TabVisibilityJson()
  {
    super(TabVisibility.class, new Object[] { "showBizWelcome", "showBuzz", "showFrames", "showLocal", "showPhotos", "showPlusOnes", "showStreamV2", "showVideos" });
  }

  public static TabVisibilityJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TabVisibilityJson
 * JD-Core Version:    0.6.2
 */