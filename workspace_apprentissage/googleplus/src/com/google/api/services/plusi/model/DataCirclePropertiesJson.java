package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCirclePropertiesJson extends EsJson<DataCircleProperties>
{
  static final DataCirclePropertiesJson INSTANCE = new DataCirclePropertiesJson();

  private DataCirclePropertiesJson()
  {
    super(DataCircleProperties.class, new Object[] { "backgroundImageUrl", "canHaveOutsideMembers", "category", "circleMode", "circleType", "description", "email", "forFollowing", "forSharing", "interactionsRank", "lastUpdateTime", "memberCount", "memberCountOutsideDomain", "name", "nameSortKey", "photoUrl", "selectedForChat" });
  }

  public static DataCirclePropertiesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCirclePropertiesJson
 * JD-Core Version:    0.6.2
 */