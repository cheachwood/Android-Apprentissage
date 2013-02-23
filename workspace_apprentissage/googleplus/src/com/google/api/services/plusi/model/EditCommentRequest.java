package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;

public final class EditCommentRequest extends GenericJson
{
  public String activityId;
  public String commentId;
  public EditSegments commentSegments;
  public String commentText;
  public ApiaryFields commonFields;
  public String contentFormat;
  public Boolean enableTracing;
  public String renderContextLocation;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditCommentRequest
 * JD-Core Version:    0.6.2
 */