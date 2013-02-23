package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PhotosInAlbumResponse extends GenericJson
{
  public DataAlbum album;
  public TraceRecords backendTrace;
  public String errorCode;
  public DataPhoto featuredPhoto;
  public Boolean isDownloadable;
  public DataUser owner;
  public List<DataPhoto> photo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosInAlbumResponse
 * JD-Core Version:    0.6.2
 */