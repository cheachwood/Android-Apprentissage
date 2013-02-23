package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StreamItemFilterJson extends EsJson<StreamItemFilter>
{
  static final StreamItemFilterJson INSTANCE = new StreamItemFilterJson();

  private StreamItemFilterJson()
  {
    super(StreamItemFilter.class, new Object[] { "itemTypes" });
  }

  public static StreamItemFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamItemFilterJson
 * JD-Core Version:    0.6.2
 */