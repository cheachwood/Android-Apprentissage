package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class VideoObject extends GenericJson
{
  public EmbedClientItem about;
  public List<EmbedsPerson> author;
  public String canonicalFountainStream;
  public String caption;
  public Place contentLocation;
  public String contentUrl;
  public String description;
  public String descriptionTruncated;
  public String duration;
  public String embedUrl;
  public String height;
  public Integer heightPx;
  public String imageUrl;
  public List<String> inboxFountainStream;
  public Boolean isFamilyFriendly;
  public String name;
  public String paid;
  public String playerType;
  public List<String> postmodFountainStream;
  public List<String> premodFountainStream;
  public Thumbnail proxiedThumbnail;
  public String thumbnailUrl;
  public List<String> unfilteredFountainStream;
  public String unlisted;
  public String url;
  public String width;
  public Integer widthPx;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VideoObject
 * JD-Core Version:    0.6.2
 */