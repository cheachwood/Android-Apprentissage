package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SquareResult extends GenericJson
{
  public String displayName;
  public Boolean dominant;
  public Long memberCount;
  public String photoUrl;
  public Long postCount;
  public Boolean privatePosts;
  public String snippetHtml;
  public SquareId squareId;
  public List<SquareResultSquareMember> squareOwner;
  public String tagline;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SquareResult
 * JD-Core Version:    0.6.2
 */