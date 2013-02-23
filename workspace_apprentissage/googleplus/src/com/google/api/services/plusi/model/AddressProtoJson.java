package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AddressProtoJson extends EsJson<AddressProto>
{
  static final AddressProtoJson INSTANCE = new AddressProtoJson();

  private AddressProtoJson()
  {
    super(AddressProto.class, new Object[] { AddressLinesProtoJson.class, "addressLines", AddressComponentProtoJson.class, "component", AddressComponentProtoJson.class, "crossStreet", "isMailing", "isPhysical", AddressLinesProtoJson.class, "koreanAddressMigration", "unambiguouslyDesignatesFeature" });
  }

  public static AddressProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressProtoJson
 * JD-Core Version:    0.6.2
 */