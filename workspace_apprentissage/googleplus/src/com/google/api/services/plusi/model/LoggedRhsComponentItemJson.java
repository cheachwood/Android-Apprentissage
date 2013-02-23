package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedRhsComponentItemJson extends EsJson<LoggedRhsComponentItem>
{
  static final LoggedRhsComponentItemJson INSTANCE = new LoggedRhsComponentItemJson();

  private LoggedRhsComponentItemJson()
  {
    super(LoggedRhsComponentItem.class, arrayOfObject);
  }

  public static LoggedRhsComponentItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedRhsComponentItemJson
 * JD-Core Version:    0.6.2
 */