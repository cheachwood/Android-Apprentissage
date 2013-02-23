package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientOutputDataJson extends EsJson<ClientOutputData>
{
  static final ClientOutputDataJson INSTANCE = new ClientOutputDataJson();

  private ClientOutputDataJson()
  {
    super(ClientOutputData.class, new Object[] { ClientLoggedCircleJson.class, "circle", ClientLoggedCircleMemberJson.class, "circleMember", ClientLoggedCircleJson.class, "streamFilterCircle", ClientLoggedSuggestionInfoJson.class, "suggestionInfo", ClientUserInfoJson.class, "userInfo" });
  }

  public static ClientOutputDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientOutputDataJson
 * JD-Core Version:    0.6.2
 */