package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OutputDataJson extends EsJson<OutputData>
{
  static final OutputDataJson INSTANCE = new OutputDataJson();

  private OutputDataJson()
  {
    super(OutputData.class, arrayOfObject);
  }

  public static OutputDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OutputDataJson
 * JD-Core Version:    0.6.2
 */