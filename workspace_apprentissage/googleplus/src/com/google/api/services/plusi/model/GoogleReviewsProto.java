package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class GoogleReviewsProto extends GenericJson
{
  public PlacePageLink moreReviewsLink;
  public NavbarProto navbar;
  public ResultsRangeProto resultsRange;
  public List<GoogleReviewProto> review;
  public ReviewsDataProto reviewData;
  public AggregatedReviewsProto stats;
  public StoryTitle title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.GoogleReviewsProto
 * JD-Core Version:    0.6.2
 */