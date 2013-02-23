package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PostCommentRequestJson extends EsJson<PostCommentRequest>
{
  static final PostCommentRequestJson INSTANCE = new PostCommentRequestJson();

  private PostCommentRequestJson()
  {
    super(PostCommentRequest.class, arrayOfObject);
  }

  public static PostCommentRequestJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PostCommentRequestJson
 * JD-Core Version:    0.6.2
 */