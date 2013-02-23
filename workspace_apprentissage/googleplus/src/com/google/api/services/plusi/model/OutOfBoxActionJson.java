package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutOfBoxActionJson extends EsJson<OutOfBoxAction>
{
  static final OutOfBoxActionJson INSTANCE = new OutOfBoxActionJson();

  private OutOfBoxActionJson()
  {
    super(OutOfBoxAction.class, new Object[] { "displayDelayMs", "text", "type", "url" });
  }

  public static OutOfBoxActionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutOfBoxActionJson
 * JD-Core Version:    0.6.2
 */