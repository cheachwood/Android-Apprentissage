package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LinkFragmentJson extends EsJson<LinkFragment>
{
  static final LinkFragmentJson INSTANCE = new LinkFragmentJson();

  private LinkFragmentJson()
  {
    super(LinkFragment.class, new Object[] { PlacePageLinkJson.class, "link", "postLinkText", "preLinkText" });
  }

  public static LinkFragmentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkFragmentJson
 * JD-Core Version:    0.6.2
 */