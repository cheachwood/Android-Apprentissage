package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EntityPhotoCropJson extends EsJson<EntityPhotoCrop>
{
  static final EntityPhotoCropJson INSTANCE = new EntityPhotoCropJson();

  private EntityPhotoCropJson()
  {
    super(EntityPhotoCrop.class, new Object[] { "height", "left", "photoId", "scale", "thumbnailIndex", "top", "width" });
  }

  public static EntityPhotoCropJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EntityPhotoCropJson
 * JD-Core Version:    0.6.2
 */