package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ZagatAspectRatingsProtoJson extends EsJson<ZagatAspectRatingsProto>
{
  static final ZagatAspectRatingsProtoJson INSTANCE = new ZagatAspectRatingsProtoJson();

  private ZagatAspectRatingsProtoJson()
  {
    super(ZagatAspectRatingsProto.class, new Object[] { ZagatAspectRatingProtoJson.class, "aspectRating", "gvertical", "source" });
  }

  public static ZagatAspectRatingsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ZagatAspectRatingsProtoJson
 * JD-Core Version:    0.6.2
 */