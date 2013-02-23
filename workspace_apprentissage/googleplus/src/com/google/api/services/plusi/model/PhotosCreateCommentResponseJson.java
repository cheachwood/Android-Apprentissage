package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PhotosCreateCommentResponseJson extends EsJson<PhotosCreateCommentResponse>
{
  static final PhotosCreateCommentResponseJson INSTANCE = new PhotosCreateCommentResponseJson();

  private PhotosCreateCommentResponseJson()
  {
    super(PhotosCreateCommentResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", DataCommentJson.class, "comment" });
  }

  public static PhotosCreateCommentResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosCreateCommentResponseJson
 * JD-Core Version:    0.6.2
 */