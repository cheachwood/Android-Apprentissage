package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class GoogleReviewProtoMetaAnnotationKeysJson extends EsJson<GoogleReviewProtoMetaAnnotationKeys>
{
  static final GoogleReviewProtoMetaAnnotationKeysJson INSTANCE = new GoogleReviewProtoMetaAnnotationKeysJson();

  private GoogleReviewProtoMetaAnnotationKeysJson()
  {
    super(GoogleReviewProtoMetaAnnotationKeys.class, new Object[] { "author", "groups", "idNamespace", "url" });
  }

  public static GoogleReviewProtoMetaAnnotationKeysJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewProtoMetaAnnotationKeysJson
 * JD-Core Version:    0.6.2
 */