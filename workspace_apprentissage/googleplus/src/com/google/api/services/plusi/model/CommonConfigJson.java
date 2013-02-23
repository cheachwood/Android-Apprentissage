package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommonConfigJson extends EsJson<CommonConfig>
{
  static final CommonConfigJson INSTANCE = new CommonConfigJson();

  private CommonConfigJson()
  {
    super(CommonConfig.class, new Object[] { "canonicalProfileUrl", ContactMeJson.class, "contactMe", "googleMeEnabled", "inAbuseiamQueue", IntFieldJson.class, "incomingConnections", ProfileStateJson.class, "profileState", "showFollowerCounts", SocialGraphDataJson.class, "socialGraphData", TabVisibilityJson.class, "tabVisibility", WallJson.class, "wall" });
  }

  public static CommonConfigJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommonConfigJson
 * JD-Core Version:    0.6.2
 */