package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class DataSuggestedCelebrityCategory extends GenericJson
{
  public String category;
  public String categoryName;
  public List<DataSuggestedPerson> celebrity;
  public Integer totalCelebrityCount;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.DataSuggestedCelebrityCategory
 * JD-Core Version:    0.6.2
 */