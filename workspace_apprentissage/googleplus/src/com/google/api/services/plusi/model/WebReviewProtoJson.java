package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class WebReviewProtoJson extends EsJson<WebReviewProto>
{
  static final WebReviewProtoJson INSTANCE = new WebReviewProtoJson();

  private WebReviewProtoJson()
  {
    super(WebReviewProto.class, new Object[] { AuthorProtoJson.class, "author", "crowded", "date", PlacePageLinkJson.class, "fullReviewLink", "reviewNum", "snippet", "sourceName", "title" });
  }

  public static WebReviewProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WebReviewProtoJson
 * JD-Core Version:    0.6.2
 */