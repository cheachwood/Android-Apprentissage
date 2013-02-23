package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ExampleObjectAttendeeJson extends EsJson<ExampleObjectAttendee>
{
  static final ExampleObjectAttendeeJson INSTANCE = new ExampleObjectAttendeeJson();

  private ExampleObjectAttendeeJson()
  {
    super(ExampleObjectAttendee.class, arrayOfObject);
  }

  public static ExampleObjectAttendeeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ExampleObjectAttendeeJson
 * JD-Core Version:    0.6.2
 */