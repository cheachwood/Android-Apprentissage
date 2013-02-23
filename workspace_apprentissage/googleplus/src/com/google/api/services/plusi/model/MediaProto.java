package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MediaProto extends GenericJson
{
  public String authorName;
  public LatLngProto latLng;
  public Integer originalIndex;
  public String reviewId;
  public Integer sourceId;
  public String sourceName;
  public MediaProtoThumbnail thumbnail;
  public List<MediaProtoThumbnail> thumbnails;
  public String title;
  public String type;
  public UserMediaProto userMedia;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MediaProto
 * JD-Core Version:    0.6.2
 */