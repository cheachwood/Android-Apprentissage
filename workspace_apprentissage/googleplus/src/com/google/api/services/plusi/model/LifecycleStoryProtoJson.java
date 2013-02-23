package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class LifecycleStoryProtoJson extends EsJson<LifecycleStoryProto>
{
  static final LifecycleStoryProtoJson INSTANCE = new LifecycleStoryProtoJson();

  private LifecycleStoryProtoJson()
  {
    super(LifecycleStoryProto.class, new Object[] { PlacePageLinkJson.class, "link", "type" });
  }

  public static LifecycleStoryProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LifecycleStoryProtoJson
 * JD-Core Version:    0.6.2
 */