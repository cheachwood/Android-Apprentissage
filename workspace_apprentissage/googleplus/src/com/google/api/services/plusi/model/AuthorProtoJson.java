package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AuthorProtoJson extends EsJson<AuthorProto>
{
  static final AuthorProtoJson INSTANCE = new AuthorProtoJson();

  private AuthorProtoJson()
  {
    super(AuthorProto.class, new Object[] { "mapsProfileId", "profileId", PlacePageLinkJson.class, "profileLink", "profilePhotoUrl", "source" });
  }

  public static AuthorProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AuthorProtoJson
 * JD-Core Version:    0.6.2
 */