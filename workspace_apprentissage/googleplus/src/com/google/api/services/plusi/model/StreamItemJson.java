package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StreamItemJson extends EsJson<StreamItem>
{
  static final StreamItemJson INSTANCE = new StreamItemJson();

  private StreamItemJson()
  {
    super(StreamItem.class, new Object[] { "type", UpdateJson.class, "updateItem" });
  }

  public static StreamItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamItemJson
 * JD-Core Version:    0.6.2
 */