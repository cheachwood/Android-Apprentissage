package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class ProviderBlockProto extends GenericJson
{
  public AggregatedReviewsProto aggregatedReviews;
  public List<AwardProto> award;
  public String faviconUrl;
  public String hostName;
  public Integer originalIndex;
  public List<RealtimeUpdateProto> realtimeUpdate;
  public List<WebReviewProto> review;
  public PlacePageLink title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ProviderBlockProto
 * JD-Core Version:    0.6.2
 */