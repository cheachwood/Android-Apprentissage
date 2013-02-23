package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class JoinedSquareJson extends EsJson<JoinedSquare>
{
  static final JoinedSquareJson INSTANCE = new JoinedSquareJson();

  private JoinedSquareJson()
  {
    super(JoinedSquare.class, new Object[] { ViewerSquareJson.class, "viewerSquare" });
  }

  public static JoinedSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.JoinedSquareJson
 * JD-Core Version:    0.6.2
 */