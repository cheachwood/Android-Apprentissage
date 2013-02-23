package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxFieldValueJson extends EsJson<OutOfBoxFieldValue>
{
  static final OutOfBoxFieldValueJson INSTANCE = new OutOfBoxFieldValueJson();

  private OutOfBoxFieldValueJson()
  {
    super(OutOfBoxFieldValue.class, new Object[] { "boolValue", MobileCoarseDateJson.class, "dateValue", MobileGenderJson.class, "gender", "stringValue" });
  }

  public static OutOfBoxFieldValueJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxFieldValueJson
 * JD-Core Version:    0.6.2
 */