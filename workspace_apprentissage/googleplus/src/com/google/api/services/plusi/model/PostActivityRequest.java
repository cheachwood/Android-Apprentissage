package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PostActivityRequest extends GenericJson
{
  public String aclJson;
  public String activityToReshare;
  public String albumId;
  public String albumOwnerId;
  public String albumTitle;
  public String apiMode;
  public RequestsPostActivityRequestAttribution attribution;
  public String botGuardResponse;
  public ApiaryFields commonFields;
  public String contentFormat;
  public Boolean disableComments;
  public Boolean disableReshares;
  public Boolean doNotDistribute;
  public List<EmailDeliveryIndicators> emailDeliveryIndicators;
  public EmbedClientItem embed;
  public Boolean enableTracing;
  public String externalId;
  public List<String> externalSites;
  public GadgetsData gadgetsData;
  public String hostDomain;
  public Boolean isAlbumReshare;
  public Boolean isFullAlbumShare;
  public Location location;
  public String mediaJson;
  public Boolean movePhotos;
  public PhotoServiceShareActionData photosShareData;
  public String renderContextLocation;
  public String resharedUpdateId;
  public Boolean saveDefaultAcl;
  public Boolean saveSeenEmailConfirmationOob;
  public Boolean saveSeenFirstPostOob;
  public Boolean saveSeenUnderageExtendedSharingOob;
  public Boolean saveSeenUnderagePublicSharingOob;
  public Boolean saveSendImplicitInvites;
  public Boolean sendImplicitInvites;
  public SharingRoster sharingRoster;
  public Boolean shouldSyncAcl;
  public List<RequestsPostActivityRequestSquareStreamInfo> squareStreams;
  public String streamSourceId;
  public UpdateMetadata updateMetadata;
  public EditSegments updateSegments;
  public String updateText;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostActivityRequest
 * JD-Core Version:    0.6.2
 */