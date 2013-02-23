package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientLoggedRhsComponentItemJson extends EsJson<ClientLoggedRhsComponentItem>
{
  static final ClientLoggedRhsComponentItemJson INSTANCE = new ClientLoggedRhsComponentItemJson();

  private ClientLoggedRhsComponentItemJson()
  {
    super(ClientLoggedRhsComponentItem.class, new Object[] { ClientLoggedCircleJson.class, "circle", "col", "connectSiteId", "gamesLabelId", ClientLoggedHangoutJson.class, "hangout", "localWriteReviewClusterId", ClientLoggedLocalWriteReviewInfoJson.class, "localWriteReviewInfo", ClientLoggedCircleMemberJson.class, "person", "row", ClientLoggedSquareJson.class, "square", ClientLoggedSuggestionInfoJson.class, "suggestionInfo" });
  }

  public static ClientLoggedRhsComponentItemJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientLoggedRhsComponentItemJson
 * JD-Core Version:    0.6.2
 */