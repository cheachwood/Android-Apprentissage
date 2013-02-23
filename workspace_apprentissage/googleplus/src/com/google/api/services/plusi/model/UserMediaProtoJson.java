package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UserMediaProtoJson extends EsJson<UserMediaProto>
{
  static final UserMediaProtoJson INSTANCE = new UserMediaProtoJson();

  private UserMediaProtoJson()
  {
    super(UserMediaProto.class, new Object[] { AuthorProtoJson.class, "author", "mediaId", "moderationStatus", TimeProtoJson.class, "time" });
  }

  public static UserMediaProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UserMediaProtoJson
 * JD-Core Version:    0.6.2
 */