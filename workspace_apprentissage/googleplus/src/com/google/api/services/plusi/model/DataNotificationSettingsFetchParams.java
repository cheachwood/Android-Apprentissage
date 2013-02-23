package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataNotificationSettingsFetchParams extends GenericJson
{
  public Boolean fetchAlternateEmailAddress;
  public Boolean fetchPlusPageSettings;
  public Boolean fetchSettingsDescription;
  public Boolean fetchWhoCanNotifyMe;
  public String settingsType;
  public List<String> typeGroupToFetch;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataNotificationSettingsFetchParams
 * JD-Core Version:    0.6.2
 */