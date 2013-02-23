package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewerSquareJson extends EsJson<ViewerSquare>
{
  static final ViewerSquareJson INSTANCE = new ViewerSquareJson();

  private ViewerSquareJson()
  {
    super(ViewerSquare.class, new Object[] { ViewerSquareCalculatedMembershipPropertiesJson.class, "calculatedMembershipProperties", "numPeopleInCommon", SquareMemberJson.class, "peopleInCommon", SquareJson.class, "square", ViewerSquareSquareActivityStatsJson.class, "squareActivityStats", ViewerSquareSquareMemberStatsJson.class, "squareMemberStats", SquareNotificationOptionsJson.class, "squareNotificationOptions", ViewerSquareStreamListJson.class, "streams", "viewerMembershipStatus", "viewerNotificationSettings" });
  }

  public static ViewerSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerSquareJson
 * JD-Core Version:    0.6.2
 */