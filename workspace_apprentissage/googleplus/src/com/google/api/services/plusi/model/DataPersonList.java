package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataPersonList extends GenericJson
{
  public List<DataCircleMemberId> invalidMemberId;
  public List<DataCirclePerson> person;
  public List<String> syncClientInfo;
  public DataSyncStateToken syncStateToken;
  public Integer totalPeople;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPersonList
 * JD-Core Version:    0.6.2
 */