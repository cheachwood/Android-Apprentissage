package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ClientActionDataJson extends EsJson<ClientActionData>
{
  static final ClientActionDataJson INSTANCE = new ClientActionDataJson();

  private ClientActionDataJson()
  {
    super(ClientActionData.class, new Object[] { ClientLoggedAutoCompleteJson.class, "autoComplete", "autoCompleteQuery", ClientLoggedBillboardImpressionJson.class, "billboardImpression", ClientLoggedBillboardPromoActionJson.class, "billboardPromoAction", ClientLoggedCircleJson.class, "circle", ClientLoggedCircleMemberJson.class, "circleMember", "gadgetId", ClientLoggedIntrCelebsClickJson.class, "intrCelebsClick", "labelId", ClientLoggedLocalWriteReviewInfoJson.class, "localWriteReviewInfo", "obfuscatedGaiaId", "photoAlbumId", "photoId", "plusEventId", ClientLoggedRhsComponentJson.class, "rhsComponent", ClientLoggedRibbonClickJson.class, "ribbonClick", ClientLoggedRibbonOrderJson.class, "ribbonOrder", ClientLoggedShareboxInfoJson.class, "shareboxInfo", SocialadsContextJson.class, "socialadsInfo", ClientLoggedSquareJson.class, "square", ClientLoggedSuggestionInfoJson.class, "suggestionInfo", ClientLoggedSuggestionSummaryInfoJson.class, "suggestionSummaryInfo" });
  }

  public static ClientActionDataJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientActionDataJson
 * JD-Core Version:    0.6.2
 */