package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataShapeJson extends EsJson<DataShape>
{
  static final DataShapeJson INSTANCE = new DataShapeJson();

  private DataShapeJson()
  {
    super(DataShape.class, new Object[] { DataRect32Json.class, "bounds", DataUserJson.class, "creator", "id", DataRectRelativeJson.class, "relativeBounds", "source", "status", DataUserJson.class, "suggestion", "url", DataUserJson.class, "user", DataVideoThumbnailJson.class, "videoThumbnail", "viewerCanApprove", "viewerCanEdit", "viewerCanSuggest" });
  }

  public static DataShapeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataShapeJson
 * JD-Core Version:    0.6.2
 */