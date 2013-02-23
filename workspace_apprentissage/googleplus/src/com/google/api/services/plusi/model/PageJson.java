package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class PageJson extends EsJson<Page>
{
  static final PageJson INSTANCE = new PageJson();

  private PageJson()
  {
    super(Page.class, new Object[] { "blocked", ClassificationJson.class, "classification", LocalEntityInfoJson.class, "localInfo", DataPlusOneJson.class, "plusone", "type", "validAgeRestrictions" });
  }

  public static PageJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.PageJson
 * JD-Core Version:    0.6.2
 */