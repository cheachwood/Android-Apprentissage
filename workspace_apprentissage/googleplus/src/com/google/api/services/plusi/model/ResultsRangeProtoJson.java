package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ResultsRangeProtoJson extends EsJson<ResultsRangeProto>
{
  static final ResultsRangeProtoJson INSTANCE = new ResultsRangeProtoJson();

  private ResultsRangeProtoJson()
  {
    super(ResultsRangeProto.class, new Object[] { "resultsRange" });
  }

  public static ResultsRangeProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ResultsRangeProtoJson
 * JD-Core Version:    0.6.2
 */