package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StreamParamsFilterJson extends EsJson<StreamParamsFilter>
{
  static final StreamParamsFilterJson INSTANCE = new StreamParamsFilterJson();

  private StreamParamsFilterJson()
  {
    super(StreamParamsFilter.class, new Object[] { "filterByExactSourceId", "filterBySourceId" });
  }

  public static StreamParamsFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamParamsFilterJson
 * JD-Core Version:    0.6.2
 */