package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataCirclePerson extends GenericJson
{
  public List<DataCircleMemberId> joinKey;
  public DataCircleMemberId memberId;
  public DataCircleMemberProperties memberProperties;
  public List<DataMembership> membership;
  public DataContact primaryContact;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataCirclePerson
 * JD-Core Version:    0.6.2
 */