package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MemberListJson extends EsJson<MemberList>
{
  static final MemberListJson INSTANCE = new MemberListJson();

  private MemberListJson()
  {
    super(MemberList.class, new Object[] { "continuationToken", SquareMemberJson.class, "member", "membershipStatus", "totalMembers" });
  }

  public static MemberListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MemberListJson
 * JD-Core Version:    0.6.2
 */