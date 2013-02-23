package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StreamJson extends EsJson<Stream>
{
  static final StreamJson INSTANCE = new StreamJson();

  private StreamJson()
  {
    super(Stream.class, new Object[] { "continuationToken", DebugJson.class, "debugInfo", StreamItemJson.class, "item", "mixerDebugInfo", StreamParamsJson.class, "params", PopularUpdatesJson.class, "popularUpdates", UpdateJson.class, "update", "volume" });
  }

  public static StreamJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StreamJson
 * JD-Core Version:    0.6.2
 */