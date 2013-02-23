package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CheckinJson extends EsJson<Checkin>
{
  static final CheckinJson INSTANCE = new CheckinJson();

  private CheckinJson()
  {
    super(Checkin.class, new Object[] { "description", PlaceJson.class, "location", "name", "startDate", "url" });
  }

  public static CheckinJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CheckinJson
 * JD-Core Version:    0.6.2
 */