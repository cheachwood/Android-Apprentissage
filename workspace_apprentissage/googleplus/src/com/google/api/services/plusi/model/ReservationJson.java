package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReservationJson extends EsJson<Reservation>
{
  static final ReservationJson INSTANCE = new ReservationJson();

  private ReservationJson()
  {
    super(Reservation.class, new Object[] { "attendeeCount", "name", "startDate", "url" });
  }

  public static ReservationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReservationJson
 * JD-Core Version:    0.6.2
 */