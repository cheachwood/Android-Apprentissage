package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusOneResponseJson extends EsJson<PlusOneResponse>
{
  static final PlusOneResponseJson INSTANCE = new PlusOneResponseJson();

  private PlusOneResponseJson()
  {
    super(PlusOneResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataPlusOneJson.class, "plusOne", "success" });
  }

  public static PlusOneResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusOneResponseJson
 * JD-Core Version:    0.6.2
 */