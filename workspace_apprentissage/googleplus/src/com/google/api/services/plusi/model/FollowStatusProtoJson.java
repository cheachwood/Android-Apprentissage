package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FollowStatusProtoJson extends EsJson<FollowStatusProto>
{
  static final FollowStatusProtoJson INSTANCE = new FollowStatusProtoJson();

  private FollowStatusProtoJson()
  {
    super(FollowStatusProto.class, new Object[] { "circleName", "isBlocked" });
  }

  public static FollowStatusProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FollowStatusProtoJson
 * JD-Core Version:    0.6.2
 */