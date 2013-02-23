package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class StoryRequestProtoJson extends EsJson<StoryRequestProto>
{
  static final StoryRequestProtoJson INSTANCE = new StoryRequestProtoJson();

  private StoryRequestProtoJson()
  {
    super(StoryRequestProto.class, new Object[] { CompositeStoryRequestProtoJson.class, "compositeRequest", GoogleReviewsRequestProtoJson.class, "googleReviewsRequest", "maxNum", "snippetTag", "start", StaticMapRequestProtoJson.class, "staticMapRequest", "type" });
  }

  public static StoryRequestProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.StoryRequestProtoJson
 * JD-Core Version:    0.6.2
 */