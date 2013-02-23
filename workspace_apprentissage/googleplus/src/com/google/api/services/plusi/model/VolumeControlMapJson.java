package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VolumeControlMapJson extends EsJson<VolumeControlMap>
{
  static final VolumeControlMapJson INSTANCE = new VolumeControlMapJson();

  private VolumeControlMapJson()
  {
    super(VolumeControlMap.class, new Object[] { VolumeControlPairJson.class, "setting" });
  }

  public static VolumeControlMapJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VolumeControlMapJson
 * JD-Core Version:    0.6.2
 */