package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareVisibilityJson extends EsJson<SquareVisibility>
{
  static final SquareVisibilityJson INSTANCE = new SquareVisibilityJson();

  private SquareVisibilityJson()
  {
    super(SquareVisibility.class, new Object[] { "memberList", "posts" });
  }

  public static SquareVisibilityJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareVisibilityJson
 * JD-Core Version:    0.6.2
 */