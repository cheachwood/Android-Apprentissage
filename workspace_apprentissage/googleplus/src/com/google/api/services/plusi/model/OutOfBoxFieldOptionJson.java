package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxFieldOptionJson extends EsJson<OutOfBoxFieldOption>
{
  static final OutOfBoxFieldOptionJson INSTANCE = new OutOfBoxFieldOptionJson();

  private OutOfBoxFieldOptionJson()
  {
    super(OutOfBoxFieldOption.class, new Object[] { "label", OutOfBoxFieldValueJson.class, "value" });
  }

  public static OutOfBoxFieldOptionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxFieldOptionJson
 * JD-Core Version:    0.6.2
 */