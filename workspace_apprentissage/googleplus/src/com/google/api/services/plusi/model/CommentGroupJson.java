package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CommentGroupJson extends EsJson<CommentGroup>
{
  static final CommentGroupJson INSTANCE = new CommentGroupJson();

  private CommentGroupJson()
  {
    super(CommentGroup.class, arrayOfObject);
  }

  public static CommentGroupJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommentGroupJson
 * JD-Core Version:    0.6.2
 */