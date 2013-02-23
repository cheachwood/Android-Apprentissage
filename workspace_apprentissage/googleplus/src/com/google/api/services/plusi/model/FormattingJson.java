package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FormattingJson extends EsJson<Formatting>
{
  static final FormattingJson INSTANCE = new FormattingJson();

  private FormattingJson()
  {
    super(Formatting.class, new Object[] { "bold", "italics", "strikethrough" });
  }

  public static FormattingJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FormattingJson
 * JD-Core Version:    0.6.2
 */