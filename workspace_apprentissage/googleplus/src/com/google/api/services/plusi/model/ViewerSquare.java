package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ViewerSquare extends GenericJson
{
  public ViewerSquareCalculatedMembershipProperties calculatedMembershipProperties;
  public Integer numPeopleInCommon;
  public List<SquareMember> peopleInCommon;
  public Square square;
  public ViewerSquareSquareActivityStats squareActivityStats;
  public ViewerSquareSquareMemberStats squareMemberStats;
  public SquareNotificationOptions squareNotificationOptions;
  public ViewerSquareStreamList streams;
  public String viewerMembershipStatus;
  public String viewerNotificationSettings;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerSquare
 * JD-Core Version:    0.6.2
 */