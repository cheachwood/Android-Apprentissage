package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmploymentJson extends EsJson<Employment>
{
  static final EmploymentJson INSTANCE = new EmploymentJson();

  private EmploymentJson()
  {
    super(Employment.class, new Object[] { "canonicalEntityGraphId", DateInfoJson.class, "dateInfo", "employer", "title" });
  }

  public static EmploymentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmploymentJson
 * JD-Core Version:    0.6.2
 */