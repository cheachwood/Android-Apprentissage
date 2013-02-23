package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VolumeControlPairJson extends EsJson<VolumeControlPair>
{
  static final VolumeControlPairJson INSTANCE = new VolumeControlPairJson();

  private VolumeControlPairJson()
  {
    super(VolumeControlPair.class, new Object[] { VolumeControlKeyJson.class, "key", "value" });
  }

  public static VolumeControlPairJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VolumeControlPairJson
 * JD-Core Version:    0.6.2
 */