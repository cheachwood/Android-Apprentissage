package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetVolumeControlsRequestJson extends EsJson<SetVolumeControlsRequest>
{
  static final SetVolumeControlsRequestJson INSTANCE = new SetVolumeControlsRequestJson();

  private SetVolumeControlsRequestJson()
  {
    super(SetVolumeControlsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", VolumeControlMapJson.class, "values" });
  }

  public static SetVolumeControlsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetVolumeControlsRequestJson
 * JD-Core Version:    0.6.2
 */