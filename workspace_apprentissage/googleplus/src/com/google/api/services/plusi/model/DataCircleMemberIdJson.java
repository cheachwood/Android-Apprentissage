package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCircleMemberIdJson extends EsJson<DataCircleMemberId>
{
  static final DataCircleMemberIdJson INSTANCE = new DataCircleMemberIdJson();

  private DataCircleMemberIdJson()
  {
    super(DataCircleMemberId.class, new Object[] { "contactId", "email", "obfuscatedGaiaId", "phone", "url" });
  }

  public static DataCircleMemberIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCircleMemberIdJson
 * JD-Core Version:    0.6.2
 */