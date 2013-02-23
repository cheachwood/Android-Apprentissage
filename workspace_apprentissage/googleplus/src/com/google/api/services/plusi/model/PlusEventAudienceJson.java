package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusEventAudienceJson extends EsJson<PlusEventAudience>
{
  static final PlusEventAudienceJson INSTANCE = new PlusEventAudienceJson();

  private PlusEventAudienceJson()
  {
    super(PlusEventAudience.class, new Object[] { "isDomainRestricted", "isExtendedCircles", "isPublic" });
  }

  public static PlusEventAudienceJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEventAudienceJson
 * JD-Core Version:    0.6.2
 */