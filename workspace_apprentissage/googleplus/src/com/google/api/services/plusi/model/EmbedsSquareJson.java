package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EmbedsSquareJson extends EsJson<EmbedsSquare>
{
  static final EmbedsSquareJson INSTANCE = new EmbedsSquareJson();

  private EmbedsSquareJson()
  {
    super(EmbedsSquare.class, new Object[] { "communityId", "description", "imageUrl", "name", ThumbnailJson.class, "proxiedImage", "squareId", "url" });
  }

  public static EmbedsSquareJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EmbedsSquareJson
 * JD-Core Version:    0.6.2
 */