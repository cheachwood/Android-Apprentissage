package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SummaryDetailsProto extends GenericJson
{
  public List<PlacePageLink> aggregatedAttribution;
  public List<DetailProto> detail;
  public PlacePageLink moreDetails;
  public StoryTitle title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SummaryDetailsProto
 * JD-Core Version:    0.6.2
 */