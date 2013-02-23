package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientOutputData extends GenericJson
{
  public List<ClientLoggedCircle> circle;
  public List<ClientLoggedCircleMember> circleMember;
  public ClientLoggedCircle streamFilterCircle;
  public List<ClientLoggedSuggestionInfo> suggestionInfo;
  public List<ClientUserInfo> userInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOutputData
 * JD-Core Version:    0.6.2
 */