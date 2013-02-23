package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareProfileJson extends EsJson<SquareProfile>
{
  static final SquareProfileJson INSTANCE = new SquareProfileJson();

  private SquareProfileJson()
  {
    super(SquareProfile.class, new Object[] { "aboutText", "abuseAppealState", "abuseState", "abuseType", "bannerUrl", "isDomainRestricted", PlaceJson.class, "location", "name", "photoUrl", SquareRelatedLinkJson.class, "relatedLinks", "tagline" });
  }

  public static SquareProfileJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareProfileJson
 * JD-Core Version:    0.6.2
 */