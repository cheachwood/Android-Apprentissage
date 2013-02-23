package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlusPhotoAlbum extends GenericJson
{
  public String albumId;
  public String albumSummaryType;
  public List<PlusPhoto> associatedMedia;
  public String authkey;
  public Place contentLocation;
  public String description;
  public String name;
  public String ownerObfuscatedId;
  public Integer photoCount;
  public String relativeUrl;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotoAlbum
 * JD-Core Version:    0.6.2
 */