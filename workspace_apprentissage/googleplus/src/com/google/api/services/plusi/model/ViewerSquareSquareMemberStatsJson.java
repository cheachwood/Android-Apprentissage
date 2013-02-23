package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewerSquareSquareMemberStatsJson extends EsJson<ViewerSquareSquareMemberStats>
{
  static final ViewerSquareSquareMemberStatsJson INSTANCE = new ViewerSquareSquareMemberStatsJson();

  private ViewerSquareSquareMemberStatsJson()
  {
    super(ViewerSquareSquareMemberStats.class, new Object[] { "memberCount", "ownerCount" });
  }

  public static ViewerSquareSquareMemberStatsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerSquareSquareMemberStatsJson
 * JD-Core Version:    0.6.2
 */