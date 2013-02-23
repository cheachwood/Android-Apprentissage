package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataClientPoliciesJson extends EsJson<DataClientPolicies>
{
  static final DataClientPoliciesJson INSTANCE = new DataClientPoliciesJson();

  private DataClientPoliciesJson()
  {
    super(DataClientPolicies.class, new Object[] { "policy" });
  }

  public static DataClientPoliciesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataClientPoliciesJson
 * JD-Core Version:    0.6.2
 */