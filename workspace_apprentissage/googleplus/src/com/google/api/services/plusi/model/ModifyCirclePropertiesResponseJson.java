package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ModifyCirclePropertiesResponseJson extends EsJson<ModifyCirclePropertiesResponse>
{
  static final ModifyCirclePropertiesResponseJson INSTANCE = new ModifyCirclePropertiesResponseJson();

  private ModifyCirclePropertiesResponseJson()
  {
    super(ModifyCirclePropertiesResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "category", "nameSortKey" });
  }

  public static ModifyCirclePropertiesResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ModifyCirclePropertiesResponseJson
 * JD-Core Version:    0.6.2
 */