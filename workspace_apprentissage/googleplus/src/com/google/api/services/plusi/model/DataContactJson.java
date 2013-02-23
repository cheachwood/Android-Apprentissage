package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataContactJson extends EsJson<DataContact>
{
  static final DataContactJson INSTANCE = new DataContactJson();

  private DataContactJson()
  {
    super(DataContact.class, new Object[] { DataContactGroupMembershipJson.class, "contactGroup", "id" });
  }

  public static DataContactJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataContactJson
 * JD-Core Version:    0.6.2
 */