package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LinkJson extends EsJson<Link>
{
  static final LinkJson INSTANCE = new LinkJson();

  private LinkJson()
  {
    super(Link.class, new Object[] { "clickUrl", PicasaAlbumJson.class, "picasaAlbum", "type", "url" });
  }

  public static LinkJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LinkJson
 * JD-Core Version:    0.6.2
 */