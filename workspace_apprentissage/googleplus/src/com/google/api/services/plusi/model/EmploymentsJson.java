package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmploymentsJson extends EsJson<Employments>
{
  static final EmploymentsJson INSTANCE = new EmploymentsJson();

  private EmploymentsJson()
  {
    super(Employments.class, new Object[] { EmploymentJson.class, "employment", MetadataJson.class, "metadata" });
  }

  public static EmploymentsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmploymentsJson
 * JD-Core Version:    0.6.2
 */