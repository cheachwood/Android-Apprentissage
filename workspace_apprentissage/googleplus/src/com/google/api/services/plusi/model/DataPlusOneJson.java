package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class DataPlusOneJson extends EsJson<DataPlusOne>
{
  static final DataPlusOneJson INSTANCE = new DataPlusOneJson();

  private DataPlusOneJson()
  {
    super(DataPlusOne.class, new Object[] { "abuseToken", "aclJson", "activityId", DataAggregateJson.class, "aggregate", "authoredContent", "commentId", DataEntityJson.class, "entity", "friendCount", "generatedActivityId", "globalCount", "htmlSnippet", "id", "imageUrl", "isPlusonedByViewer", "isSharedByViewer", "origImageUrl", DataPerfectStreamInfoJson.class, "perfectStreamInfo", DataPersonJson.class, "person", "sourceUrl", "timeModifiedMs", "type", DataUrlInfoJson.class, "urlInfo" });
  }

  public static DataPlusOneJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataPlusOneJson
 * JD-Core Version:    0.6.2
 */