package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class AuthorityPageProtoJson extends EsJson<AuthorityPageProto>
{
  static final AuthorityPageProtoJson INSTANCE = new AuthorityPageProtoJson();

  private AuthorityPageProtoJson()
  {
    super(AuthorityPageProto.class, new Object[] { PlacePageLinkJson.class, "authorityLink", "ved" });
  }

  public static AuthorityPageProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.AuthorityPageProtoJson
 * JD-Core Version:    0.6.2
 */