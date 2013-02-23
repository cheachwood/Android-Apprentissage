package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class EditCommentResponseJson extends EsJson<EditCommentResponse>
{
  static final EditCommentResponseJson INSTANCE = new EditCommentResponseJson();

  private EditCommentResponseJson()
  {
    super(EditCommentResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", CommentJson.class, "comment" });
  }

  public static EditCommentResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.EditCommentResponseJson
 * JD-Core Version:    0.6.2
 */