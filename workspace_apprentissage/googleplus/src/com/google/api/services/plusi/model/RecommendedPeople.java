package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.GenericJson;
import java.util.List;

public final class RecommendedPeople extends GenericJson
{
  public String id;
  public List<EmbedsPerson> member;
  public String name;
  public EmbedsPerson sharer;
  public String url;
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.RecommendedPeople
 * JD-Core Version:    0.6.2
 */