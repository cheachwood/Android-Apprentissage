package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataContinuationTokenJson extends EsJson<DataContinuationToken>
{
  static final DataContinuationTokenJson INSTANCE = new DataContinuationTokenJson();

  private DataContinuationTokenJson()
  {
    super(DataContinuationToken.class, new Object[] { "focusIncomingEdgesToken", "gggStartToken", "personListStartIndex" });
  }

  public static DataContinuationTokenJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataContinuationTokenJson
 * JD-Core Version:    0.6.2
 */