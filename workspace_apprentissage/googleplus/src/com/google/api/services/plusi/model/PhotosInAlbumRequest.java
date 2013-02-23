package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class PhotosInAlbumRequest extends GenericJson
{
  public String authkey;
  public String collectionId;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Integer maxResults;
  public Integer offset;
  public String ownerId;
  public RequestsPhotoOptions photoOptions;
  public String photosSortOrder;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosInAlbumRequest
 * JD-Core Version:    0.6.2
 */