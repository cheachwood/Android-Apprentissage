package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StringFieldJson extends EsJson<StringField>
{
  static final StringFieldJson INSTANCE = new StringFieldJson();

  private StringFieldJson()
  {
    super(StringField.class, new Object[] { MetadataJson.class, "metadata", "value" });
  }

  public static StringFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StringFieldJson
 * JD-Core Version:    0.6.2
 */