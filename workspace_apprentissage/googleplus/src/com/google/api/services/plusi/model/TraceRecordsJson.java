package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TraceRecordsJson extends EsJson<TraceRecords>
{
  static final TraceRecordsJson INSTANCE = new TraceRecordsJson();

  private TraceRecordsJson()
  {
    super(TraceRecords.class, new Object[] { TraceRecordsRecordJson.class, "records" });
  }

  public static TraceRecordsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TraceRecordsJson
 * JD-Core Version:    0.6.2
 */