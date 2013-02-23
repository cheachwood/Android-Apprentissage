package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetSquaresOzResponseJson extends EsJson<GetSquaresOzResponse>
{
  static final GetSquaresOzResponseJson INSTANCE = new GetSquaresOzResponseJson();

  private GetSquaresOzResponseJson()
  {
    super(GetSquaresOzResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", InvitedSquareJson.class, "invitedSquare", JoinedSquareJson.class, "joinedSquare", SquaresMembershipJson.class, "squaresMembership", SuggestedSquareJson.class, "suggestedSquare" });
  }

  public static GetSquaresOzResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetSquaresOzResponseJson
 * JD-Core Version:    0.6.2
 */