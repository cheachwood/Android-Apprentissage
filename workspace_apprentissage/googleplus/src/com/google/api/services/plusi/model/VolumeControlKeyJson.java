package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class VolumeControlKeyJson extends EsJson<VolumeControlKey>
{
  static final VolumeControlKeyJson INSTANCE = new VolumeControlKeyJson();

  private VolumeControlKeyJson()
  {
    super(VolumeControlKey.class, new Object[] { "focusGroupId", "type" });
  }

  public static VolumeControlKeyJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.VolumeControlKeyJson
 * JD-Core Version:    0.6.2
 */