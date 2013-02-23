package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxTextFieldJson extends EsJson<OutOfBoxTextField>
{
  static final OutOfBoxTextFieldJson INSTANCE = new OutOfBoxTextFieldJson();

  private OutOfBoxTextFieldJson()
  {
    super(OutOfBoxTextField.class, new Object[] { "text", "textAlign" });
  }

  public static OutOfBoxTextFieldJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxTextFieldJson
 * JD-Core Version:    0.6.2
 */