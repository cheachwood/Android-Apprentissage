package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataActionJson extends EsJson<DataAction>
{
  static final DataActionJson INSTANCE = new DataActionJson();

  private DataActionJson()
  {
    super(DataAction.class, new Object[] { DataItemJson.class, "item", "type" });
  }

  public static DataActionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataActionJson
 * JD-Core Version:    0.6.2
 */