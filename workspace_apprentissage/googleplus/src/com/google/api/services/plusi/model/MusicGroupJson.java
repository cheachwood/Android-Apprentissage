package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MusicGroupJson extends EsJson<MusicGroup>
{
  static final MusicGroupJson INSTANCE = new MusicGroupJson();

  private MusicGroupJson()
  {
    super(MusicGroup.class, new Object[] { "imageUrl", "name", "url" });
  }

  public static MusicGroupJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MusicGroupJson
 * JD-Core Version:    0.6.2
 */