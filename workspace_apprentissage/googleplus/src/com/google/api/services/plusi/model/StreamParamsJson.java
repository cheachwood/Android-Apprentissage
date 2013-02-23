package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StreamParamsJson extends EsJson<StreamParams>
{
  static final StreamParamsJson INSTANCE = new StreamParamsJson();

  private StreamParamsJson()
  {
    super(StreamParams.class, arrayOfObject);
  }

  public static StreamParamsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamParamsJson
 * JD-Core Version:    0.6.2
 */