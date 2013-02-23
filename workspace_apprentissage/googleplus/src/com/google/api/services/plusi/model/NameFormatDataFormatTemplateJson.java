package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NameFormatDataFormatTemplateJson extends EsJson<NameFormatDataFormatTemplate>
{
  static final NameFormatDataFormatTemplateJson INSTANCE = new NameFormatDataFormatTemplateJson();

  private NameFormatDataFormatTemplateJson()
  {
    super(NameFormatDataFormatTemplate.class, new Object[] { "key", "value" });
  }

  public static NameFormatDataFormatTemplateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NameFormatDataFormatTemplateJson
 * JD-Core Version:    0.6.2
 */