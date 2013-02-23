package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SetVolumeControlsResponseJson extends EsJson<SetVolumeControlsResponse>
{
  static final SetVolumeControlsResponseJson INSTANCE = new SetVolumeControlsResponseJson();

  private SetVolumeControlsResponseJson()
  {
    super(SetVolumeControlsResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "value" });
  }

  public static SetVolumeControlsResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SetVolumeControlsResponseJson
 * JD-Core Version:    0.6.2
 */