package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MemberListQueryJson extends EsJson<MemberListQuery>
{
  static final MemberListQueryJson INSTANCE = new MemberListQueryJson();

  private MemberListQueryJson()
  {
    super(MemberListQuery.class, new Object[] { "continuationToken", "membershipStatus", "pageLimit" });
  }

  public static MemberListQueryJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MemberListQueryJson
 * JD-Core Version:    0.6.2
 */