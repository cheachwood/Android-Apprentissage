package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class OffenderStatusJson extends EsJson<OffenderStatus>
{
  static final OffenderStatusJson INSTANCE = new OffenderStatusJson();

  private OffenderStatusJson()
  {
    super(OffenderStatus.class, new Object[] { "abuseType", "property", "warningState" });
  }

  public static OffenderStatusJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.OffenderStatusJson
 * JD-Core Version:    0.6.2
 */