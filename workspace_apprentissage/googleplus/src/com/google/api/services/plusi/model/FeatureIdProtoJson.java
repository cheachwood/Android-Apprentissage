package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FeatureIdProtoJson extends EsJson<FeatureIdProto>
{
  static final FeatureIdProtoJson INSTANCE = new FeatureIdProtoJson();

  private FeatureIdProtoJson()
  {
    super(FeatureIdProto.class, arrayOfObject);
  }

  public static FeatureIdProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FeatureIdProtoJson
 * JD-Core Version:    0.6.2
 */