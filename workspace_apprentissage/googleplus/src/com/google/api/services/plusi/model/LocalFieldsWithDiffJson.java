package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LocalFieldsWithDiffJson extends EsJson<LocalFieldsWithDiff>
{
  static final LocalFieldsWithDiffJson INSTANCE = new LocalFieldsWithDiffJson();

  private LocalFieldsWithDiffJson()
  {
    super(LocalFieldsWithDiff.class, new Object[] { "fields" });
  }

  public static LocalFieldsWithDiffJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LocalFieldsWithDiffJson
 * JD-Core Version:    0.6.2
 */