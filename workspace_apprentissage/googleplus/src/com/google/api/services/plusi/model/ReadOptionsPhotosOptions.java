package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ReadOptionsPhotosOptions extends GenericJson
{
  public Boolean includeComments;
  public Boolean includePlusOnes;
  public Integer maxPhotos;
  public String sortCriteria;
  public String targetObfuscatedId;
  public List<String> targetedPhotoIds;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadOptionsPhotosOptions
 * JD-Core Version:    0.6.2
 */