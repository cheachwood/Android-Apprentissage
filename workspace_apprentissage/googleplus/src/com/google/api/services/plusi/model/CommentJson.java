package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommentJson extends EsJson<Comment>
{
  static final CommentJson INSTANCE = new CommentJson();

  private CommentJson()
  {
    super(Comment.class, arrayOfObject);
  }

  public static CommentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommentJson
 * JD-Core Version:    0.6.2
 */