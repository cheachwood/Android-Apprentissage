package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NameFormatDataJson extends EsJson<NameFormatData>
{
  static final NameFormatDataJson INSTANCE = new NameFormatDataJson();

  private NameFormatDataJson()
  {
    super(NameFormatData.class, new Object[] { "familyNameFirst", NameFormatDataFormatTemplateJson.class, "templates" });
  }

  public static NameFormatDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NameFormatDataJson
 * JD-Core Version:    0.6.2
 */