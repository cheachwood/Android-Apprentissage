package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ClientActionData extends GenericJson
{
  public ClientLoggedAutoComplete autoComplete;
  public String autoCompleteQuery;
  public ClientLoggedBillboardImpression billboardImpression;
  public ClientLoggedBillboardPromoAction billboardPromoAction;
  public List<ClientLoggedCircle> circle;
  public List<ClientLoggedCircleMember> circleMember;
  public String gadgetId;
  public ClientLoggedIntrCelebsClick intrCelebsClick;
  public String labelId;
  public ClientLoggedLocalWriteReviewInfo localWriteReviewInfo;
  public List<String> obfuscatedGaiaId;
  public String photoAlbumId;
  public String photoId;
  public String plusEventId;
  public ClientLoggedRhsComponent rhsComponent;
  public ClientLoggedRibbonClick ribbonClick;
  public ClientLoggedRibbonOrder ribbonOrder;
  public ClientLoggedShareboxInfo shareboxInfo;
  public SocialadsContext socialadsInfo;
  public ClientLoggedSquare square;
  public List<ClientLoggedSuggestionInfo> suggestionInfo;
  public ClientLoggedSuggestionSummaryInfo suggestionSummaryInfo;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ClientActionData
 * JD-Core Version:    0.6.2
 */