package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FeatureHintJson extends EsJson<FeatureHint>
{
  static final FeatureHintJson INSTANCE = new FeatureHintJson();

  private FeatureHintJson()
  {
    super(FeatureHint.class, new Object[] { "type" });
  }

  public static FeatureHintJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FeatureHintJson
 * JD-Core Version:    0.6.2
 */