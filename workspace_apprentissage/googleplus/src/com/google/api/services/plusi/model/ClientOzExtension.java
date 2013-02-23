package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientOzExtension extends GenericJson
{
  public String callingApplication;
  public List<ClientOzEvent> clientEvent;
  public String clientId;
  public String clientVersion;
  public Long sendTimeMsec;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOzExtension
 * JD-Core Version:    0.6.2
 */