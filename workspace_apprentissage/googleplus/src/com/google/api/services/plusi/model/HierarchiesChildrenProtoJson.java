package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HierarchiesChildrenProtoJson extends EsJson<HierarchiesChildrenProto>
{
  static final HierarchiesChildrenProtoJson INSTANCE = new HierarchiesChildrenProtoJson();

  private HierarchiesChildrenProtoJson()
  {
    super(HierarchiesChildrenProto.class, new Object[] { HierarchiesChildrenProtoChildJson.class, "child", "columnRange", PlacePageLinkJson.class, "moreLink", StoryTitleJson.class, "storyTitle", "totalChildren" });
  }

  public static HierarchiesChildrenProtoJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HierarchiesChildrenProtoJson
 * JD-Core Version:    0.6.2
 */