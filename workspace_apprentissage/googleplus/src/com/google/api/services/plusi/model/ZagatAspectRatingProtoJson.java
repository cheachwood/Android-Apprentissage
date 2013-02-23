package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ZagatAspectRatingProtoJson extends EsJson<ZagatAspectRatingProto>
{
  static final ZagatAspectRatingProtoJson INSTANCE = new ZagatAspectRatingProtoJson();

  private ZagatAspectRatingProtoJson()
  {
    super(ZagatAspectRatingProto.class, new Object[] { "isEditable", "labelDisplay", "labelId", "valueDisplay", "valueExplanation" });
  }

  public static ZagatAspectRatingProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ZagatAspectRatingProtoJson
 * JD-Core Version:    0.6.2
 */