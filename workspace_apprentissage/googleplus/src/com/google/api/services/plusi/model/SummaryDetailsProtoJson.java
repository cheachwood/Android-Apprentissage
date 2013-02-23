package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SummaryDetailsProtoJson extends EsJson<SummaryDetailsProto>
{
  static final SummaryDetailsProtoJson INSTANCE = new SummaryDetailsProtoJson();

  private SummaryDetailsProtoJson()
  {
    super(SummaryDetailsProto.class, new Object[] { PlacePageLinkJson.class, "aggregatedAttribution", DetailProtoJson.class, "detail", PlacePageLinkJson.class, "moreDetails", StoryTitleJson.class, "title" });
  }

  public static SummaryDetailsProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SummaryDetailsProtoJson
 * JD-Core Version:    0.6.2
 */