package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusEventJson extends EsJson<PlusEvent>
{
  static final PlusEventJson INSTANCE = new PlusEventJson();

  private PlusEventJson()
  {
    super(PlusEvent.class, new Object[] { "abuseStatus", "authKey", EmbedsPersonJson.class, "creator", "creatorObfuscatedId", EventCategoryJson.class, "deprecated11", "deprecated29", "deprecated6", InviteeJson.class, "deprecated9", "description", PlusEventDisplayContentJson.class, "displayContent", "endDate", EventTimeJson.class, "endTime", EventOptionsJson.class, "eventOptions", PlusEventFeatureFlagsJson.class, "featureFlags", PlusPhotoJson.class, "featuredPlusPhoto", HangoutInfoJson.class, "hangoutInfo", "id", InviteeSummaryJson.class, "inviteeSummary", "isBroadcastView", "isPublic", PlaceJson.class, "location", "name", InviteeJson.class, "photoContributor", "photoCount", "startDate", EventTimeJson.class, "startTime", ThemeJson.class, "theme", ThemeSpecificationJson.class, "themeSpecification", EventThirdPartyInfoJson.class, "thirdPartyInfo", "url", InviteeJson.class, "viewerInfo" });
  }

  public static PlusEventJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEventJson
 * JD-Core Version:    0.6.2
 */