package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AddressRangeProtoJson extends EsJson<AddressRangeProto>
{
  static final AddressRangeProtoJson INSTANCE = new AddressRangeProtoJson();

  private AddressRangeProtoJson()
  {
    super(AddressRangeProto.class, new Object[] { "number", "parameter", "parameterIsSynthesized", "prefix", "sameParity", "suffix" });
  }

  public static AddressRangeProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressRangeProtoJson
 * JD-Core Version:    0.6.2
 */