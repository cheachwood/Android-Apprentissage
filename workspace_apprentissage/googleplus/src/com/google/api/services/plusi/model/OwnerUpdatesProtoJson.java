package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OwnerUpdatesProtoJson extends EsJson<OwnerUpdatesProto>
{
  static final OwnerUpdatesProtoJson INSTANCE = new OwnerUpdatesProtoJson();

  private OwnerUpdatesProtoJson()
  {
    super(OwnerUpdatesProto.class, new Object[] { OwnerUpdateProtoJson.class, "updates" });
  }

  public static OwnerUpdatesProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OwnerUpdatesProtoJson
 * JD-Core Version:    0.6.2
 */