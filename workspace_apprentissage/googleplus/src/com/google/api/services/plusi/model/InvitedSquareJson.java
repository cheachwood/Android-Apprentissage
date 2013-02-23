package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InvitedSquareJson extends EsJson<InvitedSquare>
{
  static final InvitedSquareJson INSTANCE = new InvitedSquareJson();

  private InvitedSquareJson()
  {
    super(InvitedSquare.class, new Object[] { SquareMemberJson.class, "inviter", ViewerSquareJson.class, "viewerSquare" });
  }

  public static InvitedSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InvitedSquareJson
 * JD-Core Version:    0.6.2
 */