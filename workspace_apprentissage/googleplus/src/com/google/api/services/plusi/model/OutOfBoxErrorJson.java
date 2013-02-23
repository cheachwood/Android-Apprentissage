package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxErrorJson extends EsJson<OutOfBoxError>
{
  static final OutOfBoxErrorJson INSTANCE = new OutOfBoxErrorJson();

  private OutOfBoxErrorJson()
  {
    super(OutOfBoxError.class, new Object[] { "text", "type" });
  }

  public static OutOfBoxErrorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxErrorJson
 * JD-Core Version:    0.6.2
 */