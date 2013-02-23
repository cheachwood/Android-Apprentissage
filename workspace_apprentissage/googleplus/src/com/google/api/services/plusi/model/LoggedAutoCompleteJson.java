package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LoggedAutoCompleteJson extends EsJson<LoggedAutoComplete>
{
  static final LoggedAutoCompleteJson INSTANCE = new LoggedAutoCompleteJson();

  private LoggedAutoCompleteJson()
  {
    super(LoggedAutoComplete.class, arrayOfObject);
  }

  public static LoggedAutoCompleteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoggedAutoCompleteJson
 * JD-Core Version:    0.6.2
 */