package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusOnePostDataJson extends EsJson<PlusOnePostData>
{
  static final PlusOnePostDataJson INSTANCE = new PlusOnePostDataJson();

  private PlusOnePostDataJson()
  {
    super(PlusOnePostData.class, arrayOfObject);
  }

  public static PlusOnePostDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusOnePostDataJson
 * JD-Core Version:    0.6.2
 */