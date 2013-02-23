package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class PostCommentRequest extends GenericJson
{
  public String activityId;
  public String botGuardResponse;
  public String clientId;
  public EditSegments commentSegments;
  public String commentText;
  public ApiaryFields commonFields;
  public String contentFormat;
  public Long creationTimeMs;
  public Boolean enableTracing;
  public String renderContextLocation;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostCommentRequest
 * JD-Core Version:    0.6.2
 */