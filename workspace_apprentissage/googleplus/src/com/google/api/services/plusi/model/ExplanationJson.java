package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class ExplanationJson extends EsJson<Explanation>
{
  static final ExplanationJson INSTANCE = new ExplanationJson();

  private ExplanationJson()
  {
    super(Explanation.class, new Object[] { "type", PersonJson.class, "user" });
  }

  public static ExplanationJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.ExplanationJson
 * JD-Core Version:    0.6.2
 */