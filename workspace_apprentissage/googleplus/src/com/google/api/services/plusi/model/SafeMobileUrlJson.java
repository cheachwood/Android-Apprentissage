package com.google.api.services.plusi.model;

import com.google.android.apps.plus.json.EsJson;

public final class SafeMobileUrlJson extends EsJson<SafeMobileUrl>
{
  static final SafeMobileUrlJson INSTANCE = new SafeMobileUrlJson();

  private SafeMobileUrlJson()
  {
    super(SafeMobileUrl.class, new Object[] { "url", "urlFormat" });
  }

  public static SafeMobileUrlJson getInstance()
  {
    return INSTANCE;
  }
}

/* Location:           C:\Dev\Java\android\adt-bundle-windows\workspace\googleplus\classes_dex2jar.jar
 * Qualified Name:     com.google.api.services.plusi.model.SafeMobileUrlJson
 * JD-Core Version:    0.6.2
 */