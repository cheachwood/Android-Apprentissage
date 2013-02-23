package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class FavaDiagnosticsNamespacedTypeJson extends EsJson<FavaDiagnosticsNamespacedType>
{
  static final FavaDiagnosticsNamespacedTypeJson INSTANCE = new FavaDiagnosticsNamespacedTypeJson();

  private FavaDiagnosticsNamespacedTypeJson()
  {
    super(FavaDiagnosticsNamespacedType.class, new Object[] { "namespace", "typeNum", "typeStr" });
  }

  public static FavaDiagnosticsNamespacedTypeJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.FavaDiagnosticsNamespacedTypeJson
 * JD-Core Version:    0.6.2
 */