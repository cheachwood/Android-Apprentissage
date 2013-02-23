package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class MarkItemReadRequestJson extends EsJson<MarkItemReadRequest>
{
  static final MarkItemReadRequestJson INSTANCE = new MarkItemReadRequestJson();

  private MarkItemReadRequestJson()
  {
    super(MarkItemReadRequest.class, new Object[] { ApiaryFieldsJson.class, "commonFields", "enableTracing", "itemIds", "networkType" });
  }

  public static MarkItemReadRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MarkItemReadRequestJson
 * JD-Core Version:    0.6.2
 */