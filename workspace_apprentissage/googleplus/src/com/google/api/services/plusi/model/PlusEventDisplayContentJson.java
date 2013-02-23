package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PlusEventDisplayContentJson extends EsJson<PlusEventDisplayContent>
{
  static final PlusEventDisplayContentJson INSTANCE = new PlusEventDisplayContentJson();

  private PlusEventDisplayContentJson()
  {
    super(PlusEventDisplayContent.class, new Object[] { PlusEventAudienceJson.class, "audience", "descriptionHtml", ViewSegmentsJson.class, "descriptionSegments", "eventTimeRange", "eventTimeRangeShort", "eventTimeStart", "isEventOver", "videoEmbedUrl" });
  }

  public static PlusEventDisplayContentJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlusEventDisplayContentJson
 * JD-Core Version:    0.6.2
 */