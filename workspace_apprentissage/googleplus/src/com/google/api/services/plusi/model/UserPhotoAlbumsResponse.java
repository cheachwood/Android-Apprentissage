package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class UserPhotoAlbumsResponse extends GenericJson
{
  public List<DataAlbum> aggregateAlbum;
  public TraceRecords backendTrace;
  public List<DataAlbum> nonAggregateAlbum;
  public Integer offset;
  public DataUser owner;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserPhotoAlbumsResponse
 * JD-Core Version:    0.6.2
 */