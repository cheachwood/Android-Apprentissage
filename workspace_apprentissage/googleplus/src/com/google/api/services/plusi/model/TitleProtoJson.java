package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class TitleProtoJson extends EsJson<TitleProto>
{
  static final TitleProtoJson INSTANCE = new TitleProtoJson();

  private TitleProtoJson()
  {
    super(TitleProto.class, new Object[] { PlacePageLinkJson.class, "linkedTitle" });
  }

  public static TitleProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.TitleProtoJson
 * JD-Core Version:    0.6.2
 */