package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OwnerUpdateProtoJson extends EsJson<OwnerUpdateProto>
{
  static final OwnerUpdateProtoJson INSTANCE = new OwnerUpdateProtoJson();

  private OwnerUpdateProtoJson()
  {
    super(OwnerUpdateProto.class, arrayOfObject);
  }

  public static OwnerUpdateProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OwnerUpdateProtoJson
 * JD-Core Version:    0.6.2
 */