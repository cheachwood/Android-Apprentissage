package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PriceLevelsProtoJson extends EsJson<PriceLevelsProto>
{
  static final PriceLevelsProtoJson INSTANCE = new PriceLevelsProtoJson();

  private PriceLevelsProtoJson()
  {
    super(PriceLevelsProto.class, arrayOfObject);
  }

  public static PriceLevelsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PriceLevelsProtoJson
 * JD-Core Version:    0.6.2
 */