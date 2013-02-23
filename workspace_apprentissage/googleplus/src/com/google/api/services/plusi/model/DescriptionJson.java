package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DescriptionJson extends EsJson<Description>
{
  static final DescriptionJson INSTANCE = new DescriptionJson();

  private DescriptionJson()
  {
    super(Description.class, new Object[] { "additionalAttributionText", PlacePageLinkJson.class, "attribution", "text" });
  }

  public static DescriptionJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DescriptionJson
 * JD-Core Version:    0.6.2
 */