package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DeleteCommentRequestJson extends EsJson<DeleteCommentRequest>
{
  static final DeleteCommentRequestJson INSTANCE = new DeleteCommentRequestJson();

  private DeleteCommentRequestJson()
  {
    super(DeleteCommentRequest.class, new Object[] { "commentId", ApiaryFieldsJson.class, "commonFields", "enableTracing" });
  }

  public static DeleteCommentRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DeleteCommentRequestJson
 * JD-Core Version:    0.6.2
 */