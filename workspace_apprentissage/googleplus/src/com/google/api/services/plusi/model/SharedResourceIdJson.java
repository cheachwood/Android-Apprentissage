package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SharedResourceIdJson extends EsJson<SharedResourceId>
{
  static final SharedResourceIdJson INSTANCE = new SharedResourceIdJson();

  private SharedResourceIdJson()
  {
    super(SharedResourceId.class, new Object[] { "applicationId", "itemId" });
  }

  public static SharedResourceIdJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SharedResourceIdJson
 * JD-Core Version:    0.6.2
 */