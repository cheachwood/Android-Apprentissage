package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MarkItemReadResponseJson extends EsJson<MarkItemReadResponse>
{
  static final MarkItemReadResponseJson INSTANCE = new MarkItemReadResponseJson();

  private MarkItemReadResponseJson()
  {
    super(MarkItemReadResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace" });
  }

  public static MarkItemReadResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MarkItemReadResponseJson
 * JD-Core Version:    0.6.2
 */