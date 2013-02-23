package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NameDisplayOptionsJson extends EsJson<NameDisplayOptions>
{
  static final NameDisplayOptionsJson INSTANCE = new NameDisplayOptionsJson();

  private NameDisplayOptionsJson()
  {
    super(NameDisplayOptions.class, new Object[] { "displayFormat", MetadataJson.class, "metadata" });
  }

  public static NameDisplayOptionsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NameDisplayOptionsJson
 * JD-Core Version:    0.6.2
 */