package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataCommentJson extends EsJson<DataComment>
{
  static final DataCommentJson INSTANCE = new DataCommentJson();

  private DataCommentJson()
  {
    super(DataComment.class, arrayOfObject);
  }

  public static DataCommentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCommentJson
 * JD-Core Version:    0.6.2
 */