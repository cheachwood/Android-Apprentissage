package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataMembershipJson extends EsJson<DataMembership>
{
  static final DataMembershipJson INSTANCE = new DataMembershipJson();

  private DataMembershipJson()
  {
    super(DataMembership.class, new Object[] { DataCircleIdJson.class, "circleId", "deleted", "memberType", "obfuscatedInviterGaiaId" });
  }

  public static DataMembershipJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataMembershipJson
 * JD-Core Version:    0.6.2
 */