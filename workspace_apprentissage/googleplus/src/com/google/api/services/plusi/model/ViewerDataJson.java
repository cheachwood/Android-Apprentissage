package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewerDataJson extends EsJson<ViewerData>
{
  static final ViewerDataJson INSTANCE = new ViewerDataJson();

  private ViewerDataJson()
  {
    super(ViewerData.class, new Object[] { "actualUserObfuscatedId", "ageInYears", AllFocusGroupsJson.class, "allFocusGroups", "birthdayHasYear", "contactMatchingOptIn", "emailAddress", LinksJson.class, "externalLinks", "fromAddress", "geographicLocation", "hasEsMobile", "hasVoiceAccountDeprecated", "isBuzzUser", "isDefaultRestricted", "isDefaultSortLatest", "isFirstTimeResharePromoDismissed", "isGmailUser", "isGoogleMeUser", "isOrkutUser", "isPlusOneUser", "isProfilePublic", "locale", NameFormatDataJson.class, "nameFormatData", "navSelectedPath", "navShowChatPromo", "obfuscatedGaiaId", "outgoingAnyCircleCount", "primaryUsername", ProfileJson.class, "profile", "profileHasAcls", SegmentationInfoJson.class, "segmentationInfo", "selectedViewFilter", "sessionId", "siloPolicy", VisibleCirclesInfoJson.class, "visibleCirclesInfo" });
  }

  public static ViewerDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerDataJson
 * JD-Core Version:    0.6.2
 */