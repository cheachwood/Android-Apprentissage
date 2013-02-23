package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LoadSocialNetworkRequestPersonListOptions extends GenericJson
{
  public Boolean includeExtendedProfileInfo;
  public Boolean includePeople;
  public Integer maxPeople;
  public List<String> profileTypesToFilter;
  public DataSyncStateToken syncStateToken;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LoadSocialNetworkRequestPersonListOptions
 * JD-Core Version:    0.6.2
 */