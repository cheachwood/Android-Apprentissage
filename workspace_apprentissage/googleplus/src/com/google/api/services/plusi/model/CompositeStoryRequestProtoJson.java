package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class CompositeStoryRequestProtoJson extends EsJson<CompositeStoryRequestProto>
{
  static final CompositeStoryRequestProtoJson INSTANCE = new CompositeStoryRequestProtoJson();

  private CompositeStoryRequestProtoJson()
  {
    super(CompositeStoryRequestProto.class, new Object[] { "boxName", StoryRequestProtoJson.class, "candidateStory", "numStories" });
  }

  public static CompositeStoryRequestProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CompositeStoryRequestProtoJson
 * JD-Core Version:    0.6.2
 */