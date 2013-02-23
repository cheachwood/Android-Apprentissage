package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateJson extends EsJson<Update>
{
  static final UpdateJson INSTANCE = new UpdateJson();

  private UpdateJson()
  {
    super(Update.class, arrayOfObject);
  }

  public static UpdateJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateJson
 * JD-Core Version:    0.6.2
 */