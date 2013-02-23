package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxInputFieldJson extends EsJson<OutOfBoxInputField>
{
  static final OutOfBoxInputFieldJson INSTANCE = new OutOfBoxInputFieldJson();

  private OutOfBoxInputFieldJson()
  {
    super(OutOfBoxInputField.class, new Object[] { "hasError", "helpText", "id", "label", "mandatory", "type", OutOfBoxFieldValueJson.class, "value", OutOfBoxFieldOptionJson.class, "valueOption" });
  }

  public static OutOfBoxInputFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxInputFieldJson
 * JD-Core Version:    0.6.2
 */