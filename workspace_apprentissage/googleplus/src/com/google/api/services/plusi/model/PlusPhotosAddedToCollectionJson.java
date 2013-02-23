package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusPhotosAddedToCollectionJson extends EsJson<PlusPhotosAddedToCollection>
{
  static final PlusPhotosAddedToCollectionJson INSTANCE = new PlusPhotosAddedToCollectionJson();

  private PlusPhotosAddedToCollectionJson()
  {
    super(PlusPhotosAddedToCollection.class, new Object[] { PlusPhotoAlbumJson.class, "associatedMedia", PlusPhotoAlbumJson.class, "associatedMediaDisplay", "collectionId", "name", "ownerObfuscatedId", PlusEventJson.class, "plusEvent", "url" });
  }

  public static PlusPhotosAddedToCollectionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusPhotosAddedToCollectionJson
 * JD-Core Version:    0.6.2
 */