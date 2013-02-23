package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeviceLocationsJson extends EsJson<DeviceLocations>
{
  static final DeviceLocationsJson INSTANCE = new DeviceLocationsJson();

  private DeviceLocationsJson()
  {
    super(DeviceLocations.class, new Object[] { DeviceLocationJson.class, "deviceLocation", MetadataJson.class, "metadata" });
  }

  public static DeviceLocationsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeviceLocationsJson
 * JD-Core Version:    0.6.2
 */