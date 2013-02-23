package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class HierarchiesChildrenProto extends GenericJson
{
  public List<HierarchiesChildrenProtoChild> child;
  public List<Integer> columnRange;
  public PlacePageLink moreLink;
  public StoryTitle storyTitle;
  public Integer totalChildren;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.HierarchiesChildrenProto
 * JD-Core Version:    0.6.2
 */