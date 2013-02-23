package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class HierarchiesChildrenProtoChildJson extends EsJson<HierarchiesChildrenProtoChild>
{
  static final HierarchiesChildrenProtoChildJson INSTANCE = new HierarchiesChildrenProtoChildJson();

  private HierarchiesChildrenProtoChildJson()
  {
    super(HierarchiesChildrenProtoChild.class, new Object[] { "clusterId", "fullName", PlacePageLinkJson.class, "link", "profileId" });
  }

  public static HierarchiesChildrenProtoChildJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HierarchiesChildrenProtoChildJson
 * JD-Core Version:    0.6.2
 */