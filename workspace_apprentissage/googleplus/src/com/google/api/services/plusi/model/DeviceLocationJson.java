package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeviceLocationJson extends EsJson<DeviceLocation>
{
  static final DeviceLocationJson INSTANCE = new DeviceLocationJson();

  private DeviceLocationJson()
  {
    super(DeviceLocation.class, arrayOfObject);
  }

  public static DeviceLocationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeviceLocationJson
 * JD-Core Version:    0.6.2
 */