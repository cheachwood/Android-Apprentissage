package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TransientDataJson extends EsJson<TransientData>
{
  static final TransientDataJson INSTANCE = new TransientDataJson();

  private TransientDataJson()
  {
    super(TransientData.class, new Object[] { PlusPhotoAlbumTransientDataJson.class, "plusPhotoAlbumTransientData" });
  }

  public static TransientDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TransientDataJson
 * JD-Core Version:    0.6.2
 */