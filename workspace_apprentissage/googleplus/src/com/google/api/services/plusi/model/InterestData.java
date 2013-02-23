package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class InterestData extends GenericJson
{
  public List<InterestDataAuthor> author;
  public String sourceName;
  public String sourceUrl;
  public Long timestampMs;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestData
 * JD-Core Version:    0.6.2
 */