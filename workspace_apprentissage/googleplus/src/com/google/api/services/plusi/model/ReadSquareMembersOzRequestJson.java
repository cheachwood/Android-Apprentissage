package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadSquareMembersOzRequestJson extends EsJson<ReadSquareMembersOzRequest>
{
  static final ReadSquareMembersOzRequestJson INSTANCE = new ReadSquareMembersOzRequestJson();

  private ReadSquareMembersOzRequestJson()
  {
    super(ReadSquareMembersOzRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "consistentRead", "enableTracing", MemberListQueryJson.class, "memberListQuery", "obfuscatedSquareId" });
  }

  public static ReadSquareMembersOzRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadSquareMembersOzRequestJson
 * JD-Core Version:    0.6.2
 */