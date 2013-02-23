package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityAlbumDataJson extends EsJson<EntityAlbumData>
{
  static final EntityAlbumDataJson INSTANCE = new EntityAlbumDataJson();

  private EntityAlbumDataJson()
  {
    super(EntityAlbumData.class, new Object[] { "albumId", "ownerId", "updatesAlbum" });
  }

  public static EntityAlbumDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityAlbumDataJson
 * JD-Core Version:    0.6.2
 */