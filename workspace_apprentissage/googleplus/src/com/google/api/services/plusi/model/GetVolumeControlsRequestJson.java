package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetVolumeControlsRequestJson extends EsJson<GetVolumeControlsRequest>
{
  static final GetVolumeControlsRequestJson INSTANCE = new GetVolumeControlsRequestJson();

  private GetVolumeControlsRequestJson()
  {
    super(GetVolumeControlsRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static GetVolumeControlsRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetVolumeControlsRequestJson
 * JD-Core Version:    0.6.2
 */