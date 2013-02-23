package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostCommentResponseJson extends EsJson<PostCommentResponse>
{
  static final PostCommentResponseJson INSTANCE = new PostCommentResponseJson();

  private PostCommentResponseJson()
  {
    super(PostCommentResponse.class, new Object[] { TraceRecordsJson.class, "backendTrace", CommentJson.class, "comment" });
  }

  public static PostCommentResponseJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostCommentResponseJson
 * JD-Core Version:    0.6.2
 */