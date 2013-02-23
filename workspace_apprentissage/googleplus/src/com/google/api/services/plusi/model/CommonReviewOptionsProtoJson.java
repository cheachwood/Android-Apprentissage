package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommonReviewOptionsProtoJson extends EsJson<CommonReviewOptionsProto>
{
  static final CommonReviewOptionsProtoJson INSTANCE = new CommonReviewOptionsProtoJson();

  private CommonReviewOptionsProtoJson()
  {
    super(CommonReviewOptionsProto.class, new Object[] { "textOptions" });
  }

  public static CommonReviewOptionsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommonReviewOptionsProtoJson
 * JD-Core Version:    0.6.2
 */