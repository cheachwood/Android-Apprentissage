package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ReadSquareMembersOzResponseJson extends EsJson<ReadSquareMembersOzResponse>
{
  static final ReadSquareMembersOzResponseJson INSTANCE = new ReadSquareMembersOzResponseJson();

  private ReadSquareMembersOzResponseJson()
  {
    super(ReadSquareMembersOzResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", MemberListJson.class, "memberList", ViewerSquareJson.class, "viewerSquare" });
  }

  public static ReadSquareMembersOzResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadSquareMembersOzResponseJson
 * JD-Core Version:    0.6.2
 */