package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquaresMembershipJson extends EsJson<SquaresMembership>
{
  static final SquaresMembershipJson INSTANCE = new SquaresMembershipJson();

  private SquaresMembershipJson()
  {
    super(SquaresMembership.class, new Object[] { ViewerSquareJson.class, "square" });
  }

  public static SquaresMembershipJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquaresMembershipJson
 * JD-Core Version:    0.6.2
 */