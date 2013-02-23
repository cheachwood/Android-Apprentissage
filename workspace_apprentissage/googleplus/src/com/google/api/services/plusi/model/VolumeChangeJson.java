package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VolumeChangeJson extends EsJson<VolumeChange>
{
  static final VolumeChangeJson INSTANCE = new VolumeChangeJson();

  private VolumeChangeJson()
  {
    super(VolumeChange.class, new Object[] { "keyType", "nextVolume", "previousVolume" });
  }

  public static VolumeChangeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VolumeChangeJson
 * JD-Core Version:    0.6.2
 */