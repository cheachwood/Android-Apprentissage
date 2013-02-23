package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlusPhotosAddedToCollection extends GenericJson
{
  public List<PlusPhotoAlbum> associatedMedia;
  public PlusPhotoAlbum associatedMediaDisplay;
  public String collectionId;
  public String name;
  public String ownerObfuscatedId;
  public PlusEvent plusEvent;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotosAddedToCollection
 * JD-Core Version:    0.6.2
 */