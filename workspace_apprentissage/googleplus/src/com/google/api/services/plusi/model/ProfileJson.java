package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ProfileJson extends EsJson<Profile>
{
  static final ProfileJson INSTANCE = new ProfileJson();

  private ProfileJson()
  {
    super(Profile.class, new Object[] { StringFieldJson.class, "aboutMeHtml", BirthdayFieldJson.class, "birthday", StringFieldJson.class, "braggingRights", "canonicalProfileUrl", ClassificationJson.class, "classification", ContactMeFieldJson.class, "contactMeChat", ContactMeFieldJson.class, "contactMeEmail", ContactMeFieldJson.class, "contactMeHangout", ContactMeFieldJson.class, "contactMePhone", ContactMeFieldJson.class, "contactMeShare", LinksJson.class, "contributorToLinks", DeviceLocationsJson.class, "deviceLocations", EducationsJson.class, "educations", EmploymentsJson.class, "employments", BooleanFieldJson.class, "enableWallRead", BooleanFieldJson.class, "enableWallWrite", EntityInfoJson.class, "entityInfo", GenderJson.class, "gender", StringFieldJson.class, "googleAnalyticsWebPropertyId", "googleMeEnabled", ContactInfoJson.class, "homeContact", "inAbuseiamQueue", IntFieldJson.class, "incomingConnections", "legacyPublicUsername", LinksJson.class, "links", LocalEntityInfoJson.class, "localInfo", LocalUserActivityJson.class, "localUserActivity", "locationMapUrl", LocationsJson.class, "locations", LinksJson.class, "meLinks", NameJson.class, "name", NameDisplayOptionsJson.class, "nameDisplayOptions", NickNameJson.class, "nickName", "obfuscatedGaiaId", StringFieldJson.class, "occupation", "optedIntoLocal", LinksJson.class, "otherLinks", OtherNamesJson.class, "otherNames", "outOfBoxDismissed", "photoIsSilhouette", "photoUrl", PlusPageInfoJson.class, "plusPageInfo", ProfilesLinkJson.class, "primaryLink", "profileBirthdayMissing", ProfileCompletionStatsJson.class, "profileCompletionStats", "profilePageCrawlable", ProfileStateJson.class, "profileState", "profileType", "profileWasAgeRestricted", "publicUsername", RelationshipInterestsJson.class, "relationshipInterests", RelationshipStatusJson.class, "relationshipStatus", SharingRosterDataJson.class, "rosterData", ScrapbookInfoJson.class, "scrapbookInfo", SegmentationInfoJson.class, "segmentationInfo", "showFollowerCounts", TabVisibilityJson.class, "tabVisibility", StringFieldJson.class, "tagLine", "validAgeRestrictions", VerifiedDomainsJson.class, "verifiedDomains", ContactInfoJson.class, "workContact", YouTubeChannelInfoJson.class, "youtubeChannelInfo", LinksJson.class, "youtubeLinks" });
  }

  public static ProfileJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProfileJson
 * JD-Core Version:    0.6.2
 */