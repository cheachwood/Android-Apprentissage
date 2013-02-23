package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class Stream extends GenericJson
{
  public String continuationToken;
  public List<Debug> debugInfo;
  public List<StreamItem> item;
  public String mixerDebugInfo;
  public StreamParams params;
  public PopularUpdates popularUpdates;
  public List<Update> update;
  public String volume;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.Stream
 * JD-Core Version:    0.6.2
 */