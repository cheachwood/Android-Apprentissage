package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ViewerSquareStreamListJson extends EsJson<ViewerSquareStreamList>
{
  static final ViewerSquareStreamListJson INSTANCE = new ViewerSquareStreamListJson();

  private ViewerSquareStreamListJson()
  {
    super(ViewerSquareStreamList.class, new Object[] { SquareStreamJson.class, "squareStream" });
  }

  public static ViewerSquareStreamListJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ViewerSquareStreamListJson
 * JD-Core Version:    0.6.2
 */