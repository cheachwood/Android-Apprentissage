package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ReadSquareMembersOzRequest extends GenericJson
{
  public ApiaryFields commonFields;
  public Boolean consistentRead;
  public Boolean enableTracing;
  public List<MemberListQuery> memberListQuery;
  public String obfuscatedSquareId;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ReadSquareMembersOzRequest
 * JD-Core Version:    0.6.2
 */