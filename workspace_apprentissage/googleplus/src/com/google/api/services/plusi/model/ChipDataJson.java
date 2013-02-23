package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ChipDataJson extends EsJson<ChipData>
{
  static final ChipDataJson INSTANCE = new ChipDataJson();

  private ChipDataJson()
  {
    super(ChipData.class, new Object[] { "active", "displayNumber", "displayText", "iconCss", "iconUrl", "language", "payload", ChipDataJson.class, "subChip", "type" });
  }

  public static ChipDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ChipDataJson
 * JD-Core Version:    0.6.2
 */