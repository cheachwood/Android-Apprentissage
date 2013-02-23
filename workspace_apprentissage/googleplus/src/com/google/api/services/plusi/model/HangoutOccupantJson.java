package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HangoutOccupantJson extends EsJson<HangoutOccupant>
{
  static final HangoutOccupantJson INSTANCE = new HangoutOccupantJson();

  private HangoutOccupantJson()
  {
    super(HangoutOccupant.class, new Object[] { "avatarurl", "name", "obfuscatedGaiaId" });
  }

  public static HangoutOccupantJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HangoutOccupantJson
 * JD-Core Version:    0.6.2
 */