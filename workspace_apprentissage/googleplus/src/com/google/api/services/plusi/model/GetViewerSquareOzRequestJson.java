package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GetViewerSquareOzRequestJson extends EsJson<GetViewerSquareOzRequest>
{
  static final GetViewerSquareOzRequestJson INSTANCE = new GetViewerSquareOzRequestJson();

  private GetViewerSquareOzRequestJson()
  {
    super(GetViewerSquareOzRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "consistentRead", "enableTracing", "obfuscatedSquareId" });
  }

  public static GetViewerSquareOzRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GetViewerSquareOzRequestJson
 * JD-Core Version:    0.6.2
 */