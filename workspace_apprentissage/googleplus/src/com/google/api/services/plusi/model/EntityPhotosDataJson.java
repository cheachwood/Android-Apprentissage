package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityPhotosDataJson extends EsJson<EntityPhotosData>
{
  static final EntityPhotosDataJson INSTANCE = new EntityPhotosDataJson();

  private EntityPhotosDataJson()
  {
    super(EntityPhotosData.class, new Object[] { EntityAlbumDataJson.class, "album", "numFaces", "numPhotos", "numPhotosDeleted", "numTagged", "numTagsRemoved", "numVideos", DataPhotoJson.class, "photo", EntityPhotoCropJson.class, "photoCrop", "photoIdsWithTaggees", "taggeeOid" });
  }

  public static EntityPhotosDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityPhotosDataJson
 * JD-Core Version:    0.6.2
 */