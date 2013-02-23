package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AddressComponentProtoJson extends EsJson<AddressComponentProto>
{
  static final AddressComponentProtoJson INSTANCE = new AddressComponentProtoJson();

  private AddressComponentProtoJson()
  {
    super(AddressComponentProto.class, new Object[] { FeatureIdProtoJson.class, "featureId", "featureType", NameProtoJson.class, "parsedName", AddressRangeProtoJson.class, "range", "type" });
  }

  public static AddressComponentProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressComponentProtoJson
 * JD-Core Version:    0.6.2
 */