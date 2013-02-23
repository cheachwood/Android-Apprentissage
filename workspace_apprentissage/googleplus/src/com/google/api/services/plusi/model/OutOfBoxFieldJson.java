package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxFieldJson extends EsJson<OutOfBoxField>
{
  static final OutOfBoxFieldJson INSTANCE = new OutOfBoxFieldJson();

  private OutOfBoxFieldJson()
  {
    super(OutOfBoxField.class, new Object[] { OutOfBoxActionJson.class, "action", OutOfBoxErrorJson.class, "error", OutOfBoxImageFieldJson.class, "image", OutOfBoxInputFieldJson.class, "input", OutOfBoxTextFieldJson.class, "text" });
  }

  public static OutOfBoxFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxFieldJson
 * JD-Core Version:    0.6.2
 */