package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class UpdateFilterJson extends EsJson<UpdateFilter>
{
  static final UpdateFilterJson INSTANCE = new UpdateFilterJson();

  private UpdateFilterJson()
  {
    super(UpdateFilter.class, new Object[] { "includeNamespace" });
  }

  public static UpdateFilterJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.UpdateFilterJson
 * JD-Core Version:    0.6.2
 */