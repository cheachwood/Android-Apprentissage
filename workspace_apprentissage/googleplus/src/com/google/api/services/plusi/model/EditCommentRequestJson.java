package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditCommentRequestJson extends EsJson<EditCommentRequest>
{
  static final EditCommentRequestJson INSTANCE = new EditCommentRequestJson();

  private EditCommentRequestJson()
  {
    super(EditCommentRequest.class, new Object[] { "activityId", "commentId", EditSegmentsJson.class, "commentSegments", "commentText", ApiaryFieldsJson.class, "commonFields", "contentFormat", "enableTracing", "renderContextLocation" });
  }

  public static EditCommentRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditCommentRequestJson
 * JD-Core Version:    0.6.2
 */