package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteCommentResponseJson extends EsJson<DeleteCommentResponse>
{
  static final DeleteCommentResponseJson INSTANCE = new DeleteCommentResponseJson();

  private DeleteCommentResponseJson()
  {
    super(DeleteCommentResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", "commentId" });
  }

  public static DeleteCommentResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteCommentResponseJson
 * JD-Core Version:    0.6.2
 */