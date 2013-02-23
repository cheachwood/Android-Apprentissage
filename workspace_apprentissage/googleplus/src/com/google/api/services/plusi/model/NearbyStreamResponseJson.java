package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class NearbyStreamResponseJson extends EsJson<NearbyStreamResponse>
{
  static final NearbyStreamResponseJson INSTANCE = new NearbyStreamResponseJson();

  private NearbyStreamResponseJson()
  {
    super(NearbyStreamResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", StreamJson.class, "stream" });
  }

  public static NearbyStreamResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.NearbyStreamResponseJson
 * JD-Core Version:    0.6.2
 */