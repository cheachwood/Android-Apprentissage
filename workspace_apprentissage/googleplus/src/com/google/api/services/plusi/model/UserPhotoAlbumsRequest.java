package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class UserPhotoAlbumsRequest extends GenericJson
{
  public List<String> albumTypes;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public Integer maxPreviewCount;
  public Integer maxResults;
  public Integer offset;
  public String ownerId;
  public Boolean sharedAlbumsOnly;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserPhotoAlbumsRequest
 * JD-Core Version:    0.6.2
 */