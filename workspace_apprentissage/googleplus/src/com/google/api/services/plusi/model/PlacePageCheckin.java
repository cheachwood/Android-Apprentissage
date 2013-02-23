package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class PlacePageCheckin extends GenericJson
{
  public AuthorProto author;
  public Long dateSec;
  public List<MediaProto> media;
  public String permalinkUrl;
  public TimeProto time;
  public String title;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PlacePageCheckin
 * JD-Core Version:    0.6.2
 */