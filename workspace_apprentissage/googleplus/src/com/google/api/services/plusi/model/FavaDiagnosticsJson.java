package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FavaDiagnosticsJson extends EsJson<FavaDiagnostics>
{
  static final FavaDiagnosticsJson INSTANCE = new FavaDiagnosticsJson();

  private FavaDiagnosticsJson()
  {
    super(FavaDiagnostics.class, arrayOfObject);
  }

  public static FavaDiagnosticsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FavaDiagnosticsJson
 * JD-Core Version:    0.6.2
 */