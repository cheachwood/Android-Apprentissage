package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class SearchContext extends GenericJson
{
  public ClientOverrides clientOverrides;
  public LocationData location;
  public List<SearchContextParam> param;
  public List<ChipData> whatChip;
  public String whatQuery;
  public List<ChipData> whereChip;
  public String whereQuery;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SearchContext
 * JD-Core Version:    0.6.2
 */