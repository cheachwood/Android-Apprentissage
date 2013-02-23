package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AddressLinesProtoJson extends EsJson<AddressLinesProto>
{
  static final AddressLinesProtoJson INSTANCE = new AddressLinesProtoJson();

  private AddressLinesProtoJson()
  {
    super(AddressLinesProto.class, new Object[] { "language", "line" });
  }

  public static AddressLinesProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AddressLinesProtoJson
 * JD-Core Version:    0.6.2
 */