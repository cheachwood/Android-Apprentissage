package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class CompositeStoryRequestProto extends GenericJson
{
  public String boxName;
  public List<StoryRequestProto> candidateStory;
  public Integer numStories;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.CompositeStoryRequestProto
 * JD-Core Version:    0.6.2
 */