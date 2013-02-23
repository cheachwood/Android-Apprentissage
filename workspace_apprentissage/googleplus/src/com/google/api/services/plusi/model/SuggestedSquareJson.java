package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SuggestedSquareJson extends EsJson<SuggestedSquare>
{
  static final SuggestedSquareJson INSTANCE = new SuggestedSquareJson();

  private SuggestedSquareJson()
  {
    super(SuggestedSquare.class, new Object[] { "numPeopleInCommon", SquareMemberJson.class, "peopleInCommon", "score", ViewerSquareJson.class, "viewerSquare" });
  }

  public static SuggestedSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SuggestedSquareJson
 * JD-Core Version:    0.6.2
 */