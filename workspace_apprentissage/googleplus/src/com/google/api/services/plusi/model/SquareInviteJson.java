package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareInviteJson extends EsJson<SquareInvite>
{
  static final SquareInviteJson INSTANCE = new SquareInviteJson();

  private SquareInviteJson()
  {
    super(SquareInvite.class, new Object[] { "communityId", "description", "imageUrl", "name", ThumbnailJson.class, "proxiedImage", "squareId", "successfulInviteeEmail", "successfulInviteeObfuscatedGaiaId", "url" });
  }

  public static SquareInviteJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareInviteJson
 * JD-Core Version:    0.6.2
 */