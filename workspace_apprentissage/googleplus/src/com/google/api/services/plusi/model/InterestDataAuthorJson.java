package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class InterestDataAuthorJson extends EsJson<InterestDataAuthor>
{
  static final InterestDataAuthorJson INSTANCE = new InterestDataAuthorJson();

  private InterestDataAuthorJson()
  {
    super(InterestDataAuthor.class, new Object[] { "name", "url" });
  }

  public static InterestDataAuthorJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.InterestDataAuthorJson
 * JD-Core Version:    0.6.2
 */