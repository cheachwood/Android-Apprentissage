package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClassificationJson extends EsJson<Classification>
{
  static final ClassificationJson INSTANCE = new ClassificationJson();

  private ClassificationJson()
  {
    super(Classification.class, new Object[] { CategoryJson.class, "category", MetadataJson.class, "metadata" });
  }

  public static ClassificationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClassificationJson
 * JD-Core Version:    0.6.2
 */