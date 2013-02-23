package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PhotoServiceShareActionData extends GenericJson
{
  public String albumTitle;
  public Boolean isAlbumReshare;
  public Boolean isFullAlbumShare;
  public List<PhotoServiceMediaReference> mediaRef;
  public PhotoServiceShareActionDataAlbum targetAlbum;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotoServiceShareActionData
 * JD-Core Version:    0.6.2
 */