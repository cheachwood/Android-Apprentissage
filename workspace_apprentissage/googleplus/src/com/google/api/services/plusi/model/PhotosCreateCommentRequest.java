package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class PhotosCreateCommentRequest extends GenericJson
{
  public String authkey;
  public String comment;
  public EditSegments commentSegments;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public String obfuscatedOwnerId;
  public Long photoId;
  public Boolean returnAllComments;
  public String squareId;
  public String squareStreamId;
  public String timestamp;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PhotosCreateCommentRequest
 * JD-Core Version:    0.6.2
 */