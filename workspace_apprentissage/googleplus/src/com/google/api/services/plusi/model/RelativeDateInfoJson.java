package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class RelativeDateInfoJson extends EsJson<RelativeDateInfo>
{
  static final RelativeDateInfoJson INSTANCE = new RelativeDateInfoJson();

  private RelativeDateInfoJson()
  {
    super(RelativeDateInfo.class, new Object[] { "quantity", "unit" });
  }

  public static RelativeDateInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RelativeDateInfoJson
 * JD-Core Version:    0.6.2
 */