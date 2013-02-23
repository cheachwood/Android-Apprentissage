package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class EntityPhotosData extends GenericJson
{
  public EntityAlbumData album;
  public Integer numFaces;
  public Integer numPhotos;
  public Integer numPhotosDeleted;
  public Integer numTagged;
  public Integer numTagsRemoved;
  public Integer numVideos;
  public List<DataPhoto> photo;
  public List<EntityPhotoCrop> photoCrop;
  public List<String> photoIdsWithTaggees;
  public List<String> taggeeOid;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityPhotosData
 * JD-Core Version:    0.6.2
 */