package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosCreateCommentRequestJson extends EsJson<PhotosCreateCommentRequest>
{
  static final PhotosCreateCommentRequestJson INSTANCE = new PhotosCreateCommentRequestJson();

  private PhotosCreateCommentRequestJson()
  {
    super(PhotosCreateCommentRequest.class, arrayOfObject);
  }

  public static PhotosCreateCommentRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosCreateCommentRequestJson
 * JD-Core Version:    0.6.2
 */