package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class IntFieldJson extends EsJson<IntField>
{
  static final IntFieldJson INSTANCE = new IntFieldJson();

  private IntFieldJson()
  {
    super(IntField.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static IntFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.IntFieldJson
 * JD-Core Version:    0.6.2
 */