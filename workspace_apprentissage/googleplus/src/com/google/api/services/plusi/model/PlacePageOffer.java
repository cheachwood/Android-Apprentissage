package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlacePageOffer extends GenericJson
{
  public List<String> details;
  public String detailsSnippet;
  public Date endDate;
  public Date expiryDate;
  public String fullTitle;
  public String id;
  public String imageUrl;
  public Boolean isRelevant;
  public PlacePageLink link;
  public Integer loyaltyTier;
  public String offerSummary;
  public List<String> redemptionAvailability;
  public String requiredClientCapabilities;
  public String source;
  public Date startDate;
  public String type;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePageOffer
 * JD-Core Version:    0.6.2
 */