package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GetSquaresOzResponse extends GenericJson
{
  public TraceRecords backendTrace;
  public List<InvitedSquare> invitedSquare;
  public List<JoinedSquare> joinedSquare;
  public SquaresMembership squaresMembership;
  public List<SuggestedSquare> suggestedSquare;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSquaresOzResponse
 * JD-Core Version:    0.6.2
 */