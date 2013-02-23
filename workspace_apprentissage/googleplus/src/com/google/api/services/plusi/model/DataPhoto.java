package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataPhoto extends GenericJson
{
  public List<String> activityId;
  public DataAlbum album;
  public Integer albumSummaryRank;
  public String caption;
  public DataClusterInfo clusterInfo;
  public List<DataComment> comment;
  public Boolean copyrightViolation;
  public Boolean countryAwareTakenDown;
  public Integer croppedAreaImageHeightPixels;
  public Integer croppedAreaImageWidthPixels;
  public Integer croppedAreaLeftPixels;
  public Integer croppedAreaTopPixels;
  public DataCurationInfo curationInfo;
  public String description;
  public Long entityVersion;
  public DataExifInfo exifInfo;
  public Long fileSize;
  public Integer fullPanoHeightPixels;
  public Integer fullPanoWidthPixels;
  public DataGeoInfo geoInfo;
  public String geoLocation;
  public DataHistogram histogram;
  public String id;
  public Long imageVersion;
  public Boolean isFaceDetectionComplete;
  public Boolean isPanorama;
  public Boolean isYouTourPhoto;
  public Boolean isYoutubeVideo;
  public DataImage original;
  public DataUser owner;
  public String pageUrl;
  public String photoKey;
  public Boolean photoWasShared;
  public String pixyFilter;
  public List<PlusEvent> plusEvent;
  public DataPlusOne plusOne;
  public Boolean plusiPublic;
  public String projectionType;
  public String provider;
  public String reportAbuseToken;
  public SafeMobileUrl safeMobileUrl;
  public List<DataShape> shape;
  public String softDeleteAppealStatus;
  public Boolean softDeleted;
  public List<String> streamId;
  public List<DataImage> thumbnail;
  public Double timestampSeconds;
  public String title;
  public Integer totalComments;
  public Long totalLikes;
  public DataImage unfiltered;
  public Update update;
  public String uploadStatus;
  public Double uploadTimestampSeconds;
  public DataVideo video;
  public Long viewCount;
  public Boolean viewerCanComment;
  public Boolean viewerCanPlusOne;
  public Boolean viewerCanTag;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPhoto
 * JD-Core Version:    0.6.2
 */