package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EducationsJson extends EsJson<Educations>
{
  static final EducationsJson INSTANCE = new EducationsJson();

  private EducationsJson()
  {
    super(Educations.class, new Object[] { EducationJson.class, "education", MetadataJson.class, "metadata" });
  }

  public static EducationsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EducationsJson
 * JD-Core Version:    0.6.2
 */