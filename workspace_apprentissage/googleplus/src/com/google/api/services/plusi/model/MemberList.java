package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class MemberList extends GenericJson
{
  public String continuationToken;
  public List<SquareMember> member;
  public String membershipStatus;
  public Integer totalMembers;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.MemberList
 * JD-Core Version:    0.6.2
 */