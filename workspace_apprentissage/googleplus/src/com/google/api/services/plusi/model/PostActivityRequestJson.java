package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostActivityRequestJson extends EsJson<PostActivityRequest>
{
  static final PostActivityRequestJson INSTANCE = new PostActivityRequestJson();

  private PostActivityRequestJson()
  {
    super(PostActivityRequest.class, new Object[] { "aclJson", "activityToReshare", "albumId", "albumOwnerId", "albumTitle", "apiMode", RequestsPostActivityRequestAttributionJson.class, "attribution", "botGuardResponse", ApiaryFieldsJson.class, "commonFields", "contentFormat", "disableComments", "disableReshares", "doNotDistribute", EmailDeliveryIndicatorsJson.class, "emailDeliveryIndicators", EmbedClientItemJson.class, "embed", "enableTracing", "externalId", "externalSites", GadgetsDataJson.class, "gadgetsData", "hostDomain", "isAlbumReshare", "isFullAlbumShare", LocationJson.class, "location", "mediaJson", "movePhotos", PhotoServiceShareActionDataJson.class, "photosShareData", "renderContextLocation", "resharedUpdateId", "saveDefaultAcl", "saveSeenEmailConfirmationOob", "saveSeenFirstPostOob", "saveSeenUnderageExtendedSharingOob", "saveSeenUnderagePublicSharingOob", "saveSendImplicitInvites", "sendImplicitInvites", SharingRosterJson.class, "sharingRoster", "shouldSyncAcl", RequestsPostActivityRequestSquareStreamInfoJson.class, "squareStreams", "streamSourceId", UpdateMetadataJson.class, "updateMetadata", EditSegmentsJson.class, "updateSegments", "updateText" });
  }

  public static PostActivityRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostActivityRequestJson
 * JD-Core Version:    0.6.2
 */