package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PhotoVideoProto extends GenericJson
{
  public List<MediaProto> media;
  public PlacePageLink morePhotos;
  public PlacePageLink moreVideos;
  public NavbarProto navbar;
  public String photoSize;
  public ResultsRangeProto resultsRange;
  public Integer totalNumPhotos;
  public Integer totalNumVideos;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoVideoProto
 * JD-Core Version:    0.6.2
 */