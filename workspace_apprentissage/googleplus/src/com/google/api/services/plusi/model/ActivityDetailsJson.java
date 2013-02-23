package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ActivityDetailsJson extends EsJson<ActivityDetails>
{
  static final ActivityDetailsJson INSTANCE = new ActivityDetailsJson();

  private ActivityDetailsJson()
  {
    super(ActivityDetails.class, new Object[] { "albumSummary", "embedType", "explanationType", "isOwnerless", "isPublic", "isRead", "itemCategory", "mediaType", "mediaUrl", "metadataNamespace", "numComments", "numPlusones", "numShares", "originalPosition", "popularUpdatePosition", "sourceStreamId", "verbType" });
  }

  public static ActivityDetailsJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ActivityDetailsJson
 * JD-Core Version:    0.6.2
 */