package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleMemberPropertiesJson extends EsJson<DataCircleMemberProperties>
{
  static final DataCircleMemberPropertiesJson INSTANCE = new DataCircleMemberPropertiesJson();

  private DataCircleMemberPropertiesJson()
  {
    super(DataCircleMemberProperties.class, new Object[] { DataCircleMemberPropertiesAddressJson.class, "address", DataHovercardBannerInfoJson.class, "bannerInfo", "company", "contact", "contactId", "disallowedInteractions", "displayName", DataEmailJson.class, "email", DataCircleMemberPropertiesEntityInfoJson.class, "entityInfo", "esUser", "firstName", "firstNameSortKey", "focusPhotoUrl", "gender", "inIncomingCircle", "inSameVisibilityGroup", "interactionsRank", "interactionsRankSortKey", "inviter", "lastNameSortKey", "lastUpdateTime", "location", "occupation", DataPhoneJson.class, "phone", "photoUrl", "profileType", "school", "tagLine", "verified" });
  }

  public static DataCircleMemberPropertiesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleMemberPropertiesJson
 * JD-Core Version:    0.6.2
 */