package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class BooleanFieldJson extends EsJson<BooleanField>
{
  static final BooleanFieldJson INSTANCE = new BooleanFieldJson();

  private BooleanFieldJson()
  {
    super(BooleanField.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static BooleanFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.BooleanFieldJson
 * JD-Core Version:    0.6.2
 */