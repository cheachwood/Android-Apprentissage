package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientLoggedShareboxInfo extends GenericJson
{
  public Boolean alsoSendEmail;
  public Boolean autocheckedAlsoSendEmail;
  public Integer emailRecipients;
  public Integer postSize;
  public String postingMode;
  public List<String> shareTargetType;
  public Integer shareType;
  public Boolean sharedToEmptyCircles;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedShareboxInfo
 * JD-Core Version:    0.6.2
 */