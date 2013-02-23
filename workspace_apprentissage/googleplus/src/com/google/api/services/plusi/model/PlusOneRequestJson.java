package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusOneRequestJson extends EsJson<PlusOneRequest>
{
  static final PlusOneRequestJson INSTANCE = new PlusOneRequestJson();

  private PlusOneRequestJson()
  {
    super(PlusOneRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "isPlusone", "itemId", "type" });
  }

  public static PlusOneRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusOneRequestJson
 * JD-Core Version:    0.6.2
 */