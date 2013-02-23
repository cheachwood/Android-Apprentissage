package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class WritePlaceReviewRequest extends GenericJson
{
  public AbuseSignals abuseSignals;
  public String cid;
  public ApiaryFields commonFields;
  public Boolean enableTracing;
  public List<String> experimentId;
  public Boolean includeSuggestedPlaces;
  public List<ZagatAspectRatingProto> oldZagatAspectRatings;
  public PriceProto price;
  public PriceLevelsProto priceLevel;
  public String reviewText;
  public String source;
  public String userCountryCode;
  public String userLanguage;
  public List<ZagatAspectRatingProto> zagatAspectRatings;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.WritePlaceReviewRequest
 * JD-Core Version:    0.6.2
 */