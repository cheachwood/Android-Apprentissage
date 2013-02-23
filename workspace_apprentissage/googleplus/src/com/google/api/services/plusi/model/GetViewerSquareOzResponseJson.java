package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetViewerSquareOzResponseJson extends EsJson<GetViewerSquareOzResponse>
{
  static final GetViewerSquareOzResponseJson INSTANCE = new GetViewerSquareOzResponseJson();

  private GetViewerSquareOzResponseJson()
  {
    super(GetViewerSquareOzResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", ViewerSquareJson.class, "viewerSquare" });
  }

  public static GetViewerSquareOzResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetViewerSquareOzResponseJson
 * JD-Core Version:    0.6.2
 */