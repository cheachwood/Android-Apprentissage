package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class CommentGroup extends GenericJson
{
  public List<String> commentsAuthor;
  public Long maxTimestampUsec;
  public Long minTimestampUsec;
  public Integer numComments;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CommentGroup
 * JD-Core Version:    0.6.2
 */