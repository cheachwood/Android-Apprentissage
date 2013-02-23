package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class LiveMapsProto extends GenericJson
{
  public List<LiveMapsPPEvent> event;
  public PlacePageLink moreEventsLink;
  public List<PlacePageLink> provider;
  public StoryTitle title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.LiveMapsProto
 * JD-Core Version:    0.6.2
 */