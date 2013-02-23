package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TraceRecordsRecordJson extends EsJson<TraceRecordsRecord>
{
  static final TraceRecordsRecordJson INSTANCE = new TraceRecordsRecordJson();

  private TraceRecordsRecordJson()
  {
    super(TraceRecordsRecord.class, arrayOfObject);
  }

  public static TraceRecordsRecordJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TraceRecordsRecordJson
 * JD-Core Version:    0.6.2
 */