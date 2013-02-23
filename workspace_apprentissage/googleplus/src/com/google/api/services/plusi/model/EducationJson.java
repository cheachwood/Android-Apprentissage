package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EducationJson extends EsJson<Education>
{
  static final EducationJson INSTANCE = new EducationJson();

  private EducationJson()
  {
    super(Education.class, new Object[] { "canonicalEntityGraphId", DateInfoJson.class, "dateInfo", "majorConcentration", "school" });
  }

  public static EducationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EducationJson
 * JD-Core Version:    0.6.2
 */