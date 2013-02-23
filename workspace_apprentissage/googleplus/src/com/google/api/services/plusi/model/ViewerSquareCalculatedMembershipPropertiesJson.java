package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewerSquareCalculatedMembershipPropertiesJson extends EsJson<ViewerSquareCalculatedMembershipProperties>
{
  static final ViewerSquareCalculatedMembershipPropertiesJson INSTANCE = new ViewerSquareCalculatedMembershipPropertiesJson();

  private ViewerSquareCalculatedMembershipPropertiesJson()
  {
    super(ViewerSquareCalculatedMembershipProperties.class, new Object[] { "canInviteToSquare", "canJoin", "canRequestToJoin", "canSeeMemberList", "canSeePosts", "canShareSquare", "isAdmin", "isMember" });
  }

  public static ViewerSquareCalculatedMembershipPropertiesJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerSquareCalculatedMembershipPropertiesJson
 * JD-Core Version:    0.6.2
 */