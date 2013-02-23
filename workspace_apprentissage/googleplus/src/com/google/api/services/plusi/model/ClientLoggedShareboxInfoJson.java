package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedShareboxInfoJson extends EsJson<ClientLoggedShareboxInfo>
{
  static final ClientLoggedShareboxInfoJson INSTANCE = new ClientLoggedShareboxInfoJson();

  private ClientLoggedShareboxInfoJson()
  {
    super(ClientLoggedShareboxInfo.class, new Object[] { "alsoSendEmail", "autocheckedAlsoSendEmail", "emailRecipients", "postSize", "postingMode", "shareTargetType", "shareType", "sharedToEmptyCircles" });
  }

  public static ClientLoggedShareboxInfoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedShareboxInfoJson
 * JD-Core Version:    0.6.2
 */