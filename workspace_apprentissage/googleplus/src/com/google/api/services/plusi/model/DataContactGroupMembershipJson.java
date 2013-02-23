package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataContactGroupMembershipJson extends EsJson<DataContactGroupMembership>
{
  static final DataContactGroupMembershipJson INSTANCE = new DataContactGroupMembershipJson();

  private DataContactGroupMembershipJson()
  {
    super(DataContactGroupMembership.class, new Object[] { "groupId" });
  }

  public static DataContactGroupMembershipJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataContactGroupMembershipJson
 * JD-Core Version:    0.6.2
 */