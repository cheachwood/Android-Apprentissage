package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataWhoCanCommentSettingsJson extends EsJson<DataWhoCanCommentSettings>
{
  static final DataWhoCanCommentSettingsJson INSTANCE = new DataWhoCanCommentSettingsJson();

  private DataWhoCanCommentSettingsJson()
  {
    super(DataWhoCanCommentSettings.class, new Object[] { "aclJson" });
  }

  public static DataWhoCanCommentSettingsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataWhoCanCommentSettingsJson
 * JD-Core Version:    0.6.2
 */