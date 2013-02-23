package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataSuggestedEntityIdJson extends EsJson<DataSuggestedEntityId>
{
  static final DataSuggestedEntityIdJson INSTANCE = new DataSuggestedEntityIdJson();

  private DataSuggestedEntityIdJson()
  {
    super(DataSuggestedEntityId.class, new Object[] { "contactId", "email", "obfuscatedGaiaId", "suggestionId" });
  }

  public static DataSuggestedEntityIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestedEntityIdJson
 * JD-Core Version:    0.6.2
 */