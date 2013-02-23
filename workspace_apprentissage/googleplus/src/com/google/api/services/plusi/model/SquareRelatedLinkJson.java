package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SquareRelatedLinkJson extends EsJson<SquareRelatedLink>
{
  static final SquareRelatedLinkJson INSTANCE = new SquareRelatedLinkJson();

  private SquareRelatedLinkJson()
  {
    super(SquareRelatedLink.class, new Object[] { "label", "url" });
  }

  public static SquareRelatedLinkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareRelatedLinkJson
 * JD-Core Version:    0.6.2
 */